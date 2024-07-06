package com.itime.compoff.service.impl;

import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumCompOffUsageStatus;
import com.itime.compoff.enumeration.EnumLeaveStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.exception.*;
import com.itime.compoff.mapper.LeaveMapper;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.entity.LeaveSummary;
import com.itime.compoff.primary.entity.LeaveTransaction;
import com.itime.compoff.primary.entity.LeaveType;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
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
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
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
    CompOffTransactionRepo compOffTransactionRepo;

    @Autowired
    LeaveSummaryRepo leaveSummaryRepo;

    @Autowired
    BusinessValidationService businessValidationService;

    @Override
    public LeaveResponse saveLeave(LeaveApplyRequest applyLeaveRequest, String createdByEmpId) throws CommonException {

        EmployeeDetail employee = businessValidationService.findEmployeeDetail(Long.parseLong(applyLeaveRequest.getEmployeeId()));

        LeaveType leaveType = this.getLeaveType(Long.valueOf(applyLeaveRequest.getLeaveTypeId()));

        businessValidationService.duplicateLeaveCheck(employee, applyLeaveRequest);

        businessValidationService.validateCompOffLeave(leaveType, applyLeaveRequest);

        LeaveTransaction transactionResponse = leaveMapper.leaveModelToLeaveEntity(applyLeaveRequest, employee, leaveType);
        LeaveTransaction leaveTransaction = leaveTransactionRepo.save(transactionResponse);

        this.buildLeaveSummary(leaveType, applyLeaveRequest);
        this.updateCompoffTransaction(leaveTransaction);
        return leaveMapper.leaveEntityToModel(leaveTransaction);
    }


    private LeaveType getLeaveType(Long leaveTypeId) throws CommonException {
        return leaveTypeRepo.findByIdAndStatus(leaveTypeId, EnumStatus.ACTIVE).orElseThrow(
                () -> new DataNotFoundException(AppConstants.LEAVE_TYPE_NOT_FOUND));
    }

    private void buildLeaveSummary(LeaveType leaveType, LeaveApplyRequest applyLeaveRequest) throws CommonException {

        EmployeeDetail employee = businessValidationService.findEmployeeDetail(Long.parseLong(applyLeaveRequest.getEmployeeId()));

        Optional<LeaveSummary> leaveSummary = leaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(leaveType, employee.getId());
        if (leaveSummary.isEmpty()) {
            LeaveSummary leaveSummaryData = leaveMapper.mapLeaveSummaryEntityNew(leaveType, employee.getId());
            Double currentLeaves = leaveSummaryData.getLeavesTaken();
            if (currentLeaves != null) {
                leaveSummaryData.setLeavesTaken(currentLeaves + Double.parseDouble(applyLeaveRequest.getNoOfDays()));
            } else {
                leaveSummaryData.setLeavesTaken(Double.parseDouble(applyLeaveRequest.getNoOfDays()));
            }
            leaveSummaryData.setLeavesAvailable(Double.parseDouble(applyLeaveRequest.getNoOfDays()));
            leaveSummaryRepo.save(leaveSummaryData);
        } else {
            log.info("Checking for leave availability leaves");
            businessValidationService.validateAvailableLeave(leaveSummary, Double.valueOf(applyLeaveRequest.getNoOfDays()));

        }
    }

    public void updateLeaveTransaction(String transactionId, String status, String employeeId, String comment) throws CommonException {

        log.trace("Getting leave transaction by transaction id");
        LeaveTransaction leaveTransaction = leaveTransactionRepo.findById(Long.valueOf(transactionId))
                .orElseThrow(() -> new DataNotFoundException(ErrorMessages.RECORD_NOT_FOUND));

        businessValidationService.findEmployeeDetail(leaveTransaction.getEmployeeId());

        EmployeeDetail reportingManager = businessValidationService.findEmployeeDetail(leaveTransaction.getReportingManager());
        if (String.valueOf(leaveTransaction.getReportingManager()).equals(employeeId)) {

            if (leaveTransaction.getLeaveStatus().equals(EnumLeaveStatus.PENDING)) {

                leaveTransaction.setLeaveStatus(EnumLeaveStatus.leaveStatusValue(status));
                leaveTransaction.setApproverRemarks(comment);
                leaveTransaction.setLastUpdatedBy(reportingManager.getFirstName());
                leaveTransaction.setLastUpdatedDt(DateTimeUtils.getCurrentTimeStamp());
                leaveTransactionRepo.save(leaveTransaction);
                this.updateLeaveSummary(leaveTransaction);
                this.updateCompoffTransaction(leaveTransaction);
            } else {
                throw new StatusException(
                        ResourcesUtils.stringFormater(AppConstants.ALREADY_LEAVE_REQUEST_APPLIED, String.valueOf(leaveTransaction.getLeaveStatus()).toLowerCase()));
            }
        } else {
            throw ApplicationErrorCode.INVALID_REQUEST.getError()
                    .commonApplicationError();
        }
    }

    private void updateCompoffTransaction(LeaveTransaction leaveTransaction) throws DataNotFoundException {
        double noOfDays = leaveTransaction.getNoOfDays();

        EmployeeDetail employeeDetail = businessValidationService.findEmployeeDetail(leaveTransaction.getEmployeeId());

        List<CompOffTransaction> compOffTransactions = this.getEmployeeCompOffTransactions(employeeDetail,
                LocalDate.now().minusDays(90), LocalDate.now().plusDays(1), null);

        List<CompOffTransaction> updatedCompOffTransactions = new ArrayList<>();
        for (CompOffTransaction compOff : compOffTransactions) {
            if (compOff.getApprovedFor().getDays() >= noOfDays) {
                compOff.setCompOffUsageStatus(EnumCompOffUsageStatus.USED);
                compOff.setLeaveTransaction(leaveTransaction);
                compOff.setLastUpdatedBy(leaveTransaction.getLastUpdatedBy());
                compOff.setLastUpdatedDt(DateTimeUtils.getCurrentTimeStamp());
                updatedCompOffTransactions.add(compOff);
                noOfDays -= compOff.getApprovedFor().getDays();
            }
            if (!updatedCompOffTransactions.isEmpty()) {
                compOffTransactionRepo.saveAll(updatedCompOffTransactions);
            }
        }
    }

    private List<CompOffTransaction> getEmployeeCompOffTransactions(EmployeeDetail employeeId, LocalDate startDate, LocalDate endDate, LeaveTransaction leaveTransaction) {
        if (leaveTransaction == null) {
            return compOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc
                    (employeeId.getId(), DateTimeUtils.convertLocalDateToTimestamp(startDate), DateTimeUtils.convertLocalDateToTimestamp(endDate), EnumCompOffTransactionStatus.APPROVED, List.of(EnumCompOffUsageStatus.AVAILABLE), EnumStatus.ACTIVE);
        } else {
            return compOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc
                    (employeeId.getId(), EnumCompOffTransactionStatus.APPROVED, List.of(EnumCompOffUsageStatus.USED), EnumStatus.ACTIVE,
                            leaveTransaction);
        }
    }

    private LeaveSummary updateLeaveSummary(LeaveTransaction leaveTransaction) {

        log.info("Updating leave summary");
        Optional<LeaveSummary> leaveSummary = leaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(leaveTransaction.getLeaveTypeId(), leaveTransaction.getEmployeeId());

        Double availableLeaves = 0.0;
        Double leaveTaken;
        Long leaveSummeryId = 0L;
        Double leaveOpen = 0.0;
        Double leaveCredit = 0.0;

        if (leaveSummary.isPresent()) {
            leaveSummeryId = leaveSummary.get().getId();
            leaveOpen = leaveSummary.get().getLeaveOpen();
            leaveCredit = leaveSummary.get().getLeaveCredit();
            if (leaveTransaction.getLeaveStatus().equals(EnumLeaveStatus.REJECTED)
                    || leaveTransaction.getLeaveStatus().equals(EnumLeaveStatus.CANCELLED)) {
                leaveTaken = leaveSummary.get().getLeavesTaken() - leaveTransaction.getNoOfDays();
                availableLeaves = leaveSummary.get().getLeavesAvailable() + leaveTransaction.getNoOfDays();
            } else {
                leaveTaken = leaveSummary.get().getLeavesTaken() + leaveTransaction.getNoOfDays();
                availableLeaves = leaveSummary.get().getLeavesAvailable() - leaveTransaction.getNoOfDays();
            }
        } else {
            Optional<LeaveType> leaveType = leaveTypeRepo.findById(leaveTransaction.getLeaveTypeId().getId());
            if (leaveType.isPresent()) {
                availableLeaves = leaveType.get().getLeavesAllowed() - leaveTransaction.getNoOfDays();
            }
            leaveTaken = leaveTransaction.getNoOfDays();
        }
        log.info("Leave summary updated successfully");
        return leaveSummaryRepo
                .save(leaveMapper.mapLeaveSummaryEntity(leaveTransaction, availableLeaves, leaveTaken, leaveSummeryId, leaveOpen, leaveCredit));

    }
}

