package com.itime.compoff.service.impl;

import com.itime.compoff.enumeration.EnumCompOffPeriod;
import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumCompOffUsageStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.exception.ApplicationErrorCode;
import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
import com.itime.compoff.exception.ErrorMessages;
import com.itime.compoff.mapper.CompOffMapper;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.service.CompOffTransactionService;
import com.itime.compoff.utils.DateTimeUtils;
import com.itime.compoff.validation.BusinessValidationService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class CompOffTransactionServiceImpl implements CompOffTransactionService {

    private static final Logger log = LoggerFactory.getLogger(CompOffTransactionServiceImpl.class);
    @Autowired
    CompOffTransactionRepo compOffTransactionRepo;

    @Autowired
    CompOffMapper compOffMapper;

    @Autowired
    BusinessValidationService businessValidationService;

    // Create new Compoff apply request
    @Override
    @Transactional(rollbackOn = CommonException.class)
    public HttpStatus createCompOffTransaction(CompOffApplyRequest compOffApplyRequest) throws CommonException {

        log.info("Processing create comp-off request...");
        EmployeeDetail employee = businessValidationService.findEmployeeDetail(compOffApplyRequest.getEmployeeId());

        Timestamp requestDate = Timestamp.valueOf(compOffApplyRequest.getRequestedDate());

        businessValidationService.duplicateCompOffValidation(employee, compOffApplyRequest);

        businessValidationService.validateCompOff(compOffApplyRequest, requestDate);

        CompOffTransaction compOffTransaction = compOffMapper.mapApplyRequestToEntity(employee, compOffApplyRequest);

        compOffTransactionRepo.save(compOffTransaction);

        return HttpStatus.CREATED;
    }

    // Compoff Approval/Reject
    @Override
    public void updateCompOffApproval(String transactionId, String status, String compOffFor, String comment) throws CommonException {

        if (EnumCompOffPeriod.valuesOf(compOffFor) == null) {
            throw new CommonException(ErrorMessages.INVALID_COMPOFF_REQUEST_FOR);
        }

        CompOffTransaction compOffTransaction = compOffTransactionRepo.findTop1ByIdAndStatus(Long.parseLong(transactionId), EnumStatus.ACTIVE)
                .orElseThrow(() -> new DataNotFoundException(ErrorMessages.RECORD_NOT_FOUND));

        EmployeeDetail employee = businessValidationService.findEmployeeDetail(compOffTransaction.getEmployeeId());

        if (compOffTransaction.getTransactionStatus().equals(EnumCompOffTransactionStatus.PENDING)) {
            compOffTransaction.setTransactionStatus(EnumCompOffTransactionStatus.compOffStatusValue(status.toUpperCase()));
            compOffTransaction.setCompOffUsageStatus(compOffTransaction.getTransactionStatus().equals(EnumCompOffTransactionStatus.APPROVED) ?
                    EnumCompOffUsageStatus.AVAILABLE : EnumCompOffUsageStatus.INVALID);
            compOffTransaction.setApproverRemarks(comment);
            compOffTransaction.setLastUpdatedDt(DateTimeUtils.getCurrentTimeStamp());
            compOffTransaction.setLastUpdatedBy(employee.getApproverId().getFirstName());
            if (compOffFor != null) {
                businessValidationService.validateWorkHours
                        (null, null, EnumCompOffPeriod.valuesOf(compOffFor),
                                businessValidationService.findTimeDifference(compOffTransaction.getPunchInTime(), compOffTransaction.getPunchOutTime()));
                compOffTransaction.setApprovedFor(EnumCompOffPeriod.valuesOf(compOffFor.toUpperCase()));
            }
            compOffTransactionRepo.save(compOffTransaction);
        } else {
            throw ApplicationErrorCode.ALREADY_COMP_OFF_UPDATED.getError().reqValidationError(
                    compOffTransaction.getTransactionStatus().getCompOffStatus().toLowerCase());
        }

    }

}
