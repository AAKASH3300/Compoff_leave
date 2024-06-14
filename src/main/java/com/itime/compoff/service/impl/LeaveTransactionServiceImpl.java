package com.itime.compoff.service.impl;

import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.enumeration.EnumTrueFalse;
import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
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
import com.itime.compoff.validation.BusinessValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Component
public class LeaveTransactionServiceImpl implements LeaveTransactionService {

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
    public LeaveResponse saveLeave(LeaveApplyRequest applyLeaverequest, String createdByEmpId) throws CommonException {

        EmployeeDetail employeeDetail = businessValidationService.getEmployee(applyLeaverequest.getEmployeeId());

        LeaveType leaveType = this.getLeaveType(Long.valueOf(applyLeaverequest.getLeaveTypeId()));

        LeaveTransaction transactionResponse = leaveMapper.leaveModelToLeaveEntity(applyLeaverequest, employeeDetail, leaveType);
        LeaveTransaction leaveTransaction = leaveTransactionRepo.save(transactionResponse);
        this.buildLeaveSummary(leaveType, employeeDetail,applyLeaverequest, leaveTransaction);

        return leaveMapper.leaveEntityToModel(leaveTransaction);

    }

    private LeaveType getLeaveType(Long leaveTypeId) throws CommonException {
        return leaveTypeRepo.findByIdAndStatus(leaveTypeId, EnumStatus.ACTIVE).orElseThrow(
                () -> new DataNotFoundException(AppConstants.LEAVE_TYPE_NOT_FOUND, HttpStatus.BAD_REQUEST.value()));

    }

    private void buildLeaveSummary(LeaveType leaveType, EmployeeDetail employeeDetail,LeaveApplyRequest applyLeaverequest, LeaveTransaction leaveTransaction) {

        Optional<LeaveSummary> leaveSummary = leaveSummaryRepo.findByLeaveTypeIdAndEmployeeId( leaveType, employeeDetail);
        if (leaveSummary.isEmpty()) {
            LeaveSummary leaveSummaryData = leaveMapper.mapLeaveSummaryEntityNew(employeeDetail.getId());
            leaveSummaryData.setLeavesAvailable(Double.parseDouble(applyLeaverequest.getNoOfDays()));
            leaveSummaryData.setLeavesTaken(Double.valueOf(applyLeaverequest.getNoOfDays()));
            leaveSummaryData.setLeaveOpen(3.0);
            leaveSummaryData.setLeavesAvailable(0.0);
            leaveSummaryRepo.save(leaveSummaryData);
        }
    }
}

