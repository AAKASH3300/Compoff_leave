package com.itime.compoff.service.impl;

import com.itime.compoff.controller.CompOffTransactionController;
import com.itime.compoff.enumeration.EnumLeaveStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.exception.*;
import com.itime.compoff.mapper.LeaveMapper;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import com.itime.compoff.primary.entity.LeaveSummary;
import com.itime.compoff.primary.entity.LeaveTransaction;
import com.itime.compoff.primary.entity.LeaveType;
import com.itime.compoff.primary.repository.LeaveSummaryRepo;
import com.itime.compoff.primary.repository.LeaveTransactionRepo;
import com.itime.compoff.primary.repository.LeaveTypeRepo;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.service.LeaveTransactionService;
import com.itime.compoff.utils.AppConstants;
import com.itime.compoff.utils.DateTimeUtils;
import com.itime.compoff.utils.ResourcesUtils;
import com.itime.compoff.validation.BusinessValidationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LeaveTransactionServiceImpl implements LeaveTransactionService {

    private static final Logger log = LoggerFactory.getLogger(LeaveTransactionServiceImpl.class);

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    LeaveTypeRepo leaveTypeRepo;

    @Autowired
    LeaveTransactionRepo leaveTransactionRepo;

    @Autowired
    LeaveSummaryRepo leaveSummaryRepo;

    @Autowired
    BusinessValidationService businessValidationService;

    @Override
    public LeaveResponse saveLeave(LeaveApplyRequest applyLeaveRequest, String createdByEmpId) throws CommonException {

        EmployeeDetail employee = businessValidationService.findEmployeeDetail(Long.parseLong(applyLeaveRequest.getEmployeeId()));

        LeaveType leaveType = this.getLeaveType(Long.valueOf(applyLeaveRequest.getLeaveTypeId()));
        businessValidationService.duplicateLeaveCheck(employee, applyLeaveRequest);
        LeaveTransaction transactionResponse = leaveMapper.leaveModelToLeaveEntity(applyLeaveRequest, employee, leaveType);
        LeaveTransaction leaveTransaction = leaveTransactionRepo.save(transactionResponse);
        this.buildLeaveSummary(leaveType, applyLeaveRequest, leaveTransaction);
        return leaveMapper.leaveEntityToModel(leaveTransaction);
    }

    private LeaveType getLeaveType(Long leaveTypeId) throws CommonException {
        return leaveTypeRepo.findByIdAndStatus(leaveTypeId, EnumStatus.ACTIVE).orElseThrow(
                () -> new DataNotFoundException(AppConstants.LEAVE_TYPE_NOT_FOUND));
    }

    private void buildLeaveSummary(LeaveType leaveType, LeaveApplyRequest applyLeaverequest, LeaveTransaction leaveTransaction) throws DataNotFoundException {

        EmployeeDetail employee = businessValidationService.findEmployeeDetail(Long.parseLong(applyLeaverequest.getEmployeeId()));

        Optional<LeaveSummary> leaveSummary = leaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(leaveType, employee.getId());
        if (leaveSummary.isEmpty()) {
            LeaveSummary leaveSummaryData = leaveMapper.mapLeaveSummaryEntityNew(leaveType, employee.getId());
            leaveSummaryData.setLeavesAvailable(Double.parseDouble(applyLeaverequest.getNoOfDays()));
            leaveSummaryData.setLeavesTaken(Double.valueOf(applyLeaverequest.getNoOfDays()));
            leaveSummaryData.setLeaveOpen(3.0);
            leaveSummaryData.setLeavesAvailable(0.0);
            leaveSummaryRepo.save(leaveSummaryData);
        }
    }

    public void updateLeave(String transactionId, String status, String employeeId, String comment) throws CommonException {

        log.trace("Getting leave transaction by id");
        LeaveTransaction leaveTransaction = leaveTransactionRepo.findById(Long.valueOf(transactionId)).orElseThrow(
                () -> new DataNotFoundException(ErrorMessages.RECORD_NOT_FOUND));

        businessValidationService.findEmployeeDetail(leaveTransaction.getEmployeeId());

        EmployeeDetail reportingManager = businessValidationService.findEmployeeDetail(leaveTransaction.getReportingManager());
        if (String.valueOf(leaveTransaction.getReportingManager()).equals(employeeId)) {

            if (leaveTransaction.getLeaveStatus().equals(EnumLeaveStatus.PENDING)) {

                leaveTransaction.setLeaveStatus(EnumLeaveStatus.APPROVED);
                leaveTransaction.setApproverRemarks(comment);
                leaveTransaction.setLastUpdatedBy(reportingManager.getFirstName());
                leaveTransaction.setLastUpdatedDt(DateTimeUtils.getCurrentTimeStamp());
                leaveTransactionRepo.save(leaveTransaction);
            } else {
                throw new StatusException(
                        ResourcesUtils.stringFormater(AppConstants.ALREADY_LEAVE_REQUEST_APPLIED, String.valueOf(leaveTransaction.getLeaveStatus()).toLowerCase()));
            }
        } else {

            throw ApplicationErrorCode.INVALID_REQUEST.getError()
                    .commonApplicationError(HttpStatus.BAD_REQUEST.value());
        }
    }
}

