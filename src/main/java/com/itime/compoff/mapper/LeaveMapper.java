package com.itime.compoff.mapper;

import com.itime.compoff.enumeration.EnumLeavePeriod;
import com.itime.compoff.enumeration.EnumLeaveStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import com.itime.compoff.primary.entity.LeaveSummary;
import com.itime.compoff.primary.entity.LeaveTransaction;
import com.itime.compoff.primary.entity.LeaveType;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.utils.DateTimeUtils;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class LeaveMapper {
    public LeaveResponse leaveEntityToModel(LeaveTransaction leaveTransaction) {
        LeaveResponse applyLeaveResponse = new LeaveResponse();
        applyLeaveResponse.setEmployeeId(String.valueOf(leaveTransaction.getEmployeeId()));
        applyLeaveResponse.setLeaveTypeId(String.valueOf(leaveTransaction.getLeaveTypeId().getId()));

        applyLeaveResponse.setStartDt(DateTimeUtils.convertToJsonTimestampDateOnly(leaveTransaction.getStartDt()));
        applyLeaveResponse.setEndDt(DateTimeUtils.convertToJsonTimestampDateOnly(leaveTransaction.getEndDt()));

        applyLeaveResponse.setNoOfDays(String.valueOf(leaveTransaction.getNoOfDays()));
        applyLeaveResponse.setLeaveStatus(String.valueOf(leaveTransaction.getLeaveStatus()));
        applyLeaveResponse.setReason("Comp-Off Leave");
        applyLeaveResponse.setLeaveForStartDt(String.valueOf(leaveTransaction.getLeaveForStartDt()));
        applyLeaveResponse.setLeaveForEndDt(String.valueOf(leaveTransaction.getLeaveForEndDt()));
        applyLeaveResponse.setReportingManager(String.valueOf(leaveTransaction.getReportingManager()));
        applyLeaveResponse.setStatus(String.valueOf(leaveTransaction.getStatus()));
        applyLeaveResponse.setAppliedOn(String.valueOf(leaveTransaction.getCreatedDt()));

        applyLeaveResponse.setCreatedByEmpId(String.valueOf(leaveTransaction.getEmployeeId()));

        return applyLeaveResponse;
    }

    public LeaveSummary mapLeaveSummaryEntityNew(LeaveType leaveType, long id) {
        LeaveSummary leaveSummary = new LeaveSummary();
        EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(id);
        leaveSummary.setEmployeeId(employeeDetail.getId());
        leaveSummary.setLeaveTypeId(leaveType);
        leaveSummary.setPeriodStartDt(DateTimeUtils.getFirstDayOfYear());
        leaveSummary.setPeriodEndDt(DateTimeUtils.getLastDayOfYear());
        leaveSummary.setLeaveCredit(0.0);
        return leaveSummary;

    }
    public LeaveTransaction leaveModelToLeaveEntity(LeaveApplyRequest applyLeaverequest, EmployeeDetail employeeDetail, LeaveType leaveType) {
        LeaveTransaction leaveTransaction = new LeaveTransaction();
        leaveType.setId(Long.parseLong(applyLeaverequest.getLeaveTypeId()));

        leaveTransaction.setEmployeeId(employeeDetail.getId());
        leaveTransaction.setLeaveTypeId(leaveType);
        leaveTransaction.setStartDt(Timestamp.valueOf(applyLeaverequest.getStartDt()));

        leaveTransaction.setEndDt(applyLeaverequest.getEndDt() != null
                ? Timestamp.valueOf(applyLeaverequest.getEndDt())
                : Timestamp.valueOf(applyLeaverequest.getStartDt()));

        leaveTransaction.setLeaveForEndDt(applyLeaverequest.getLeaveForEndDt() != null
                ? EnumLeavePeriod.valueOfPeriod(applyLeaverequest.getLeaveForEndDt())
                : EnumLeavePeriod.valueOfPeriod(applyLeaverequest.getLeaveForStartDt()));

        leaveTransaction.setNoOfDays(Double.valueOf(applyLeaverequest.getNoOfDays()));
        leaveTransaction.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction.setReason(applyLeaverequest.getReason());
        leaveTransaction.setLeaveForStartDt(EnumLeavePeriod.valueOfPeriod(applyLeaverequest.getLeaveForStartDt()));
        leaveTransaction.setReportingManager(employeeDetail.getApproverId().getId());

        leaveTransaction.setStatus(EnumStatus.ACTIVE);
        leaveTransaction.setCreatedDt(DateTimeUtils.getCurrentTimeStamp());
        leaveTransaction.setCreatedBy(employeeDetail.getFirstName());
        leaveTransaction.setLastUpdatedDt(DateTimeUtils.getCurrentTimeStamp());
        leaveTransaction.setLastUpdatedBy(employeeDetail.getFirstName());

        return leaveTransaction;
    }

    public LeaveSummary mapLeaveSummaryEntity(LeaveTransaction leaveTransaction, Double availableLeaves,
                                              Double leaveTaken, Long summaryId, Double leaveOpen, Double leaveCredit) {

        LeaveSummary leaveSummary = new LeaveSummary();

        if (summaryId != null) {
            leaveSummary.setId(summaryId);
        }
        leaveSummary.setLeavesAvailable(availableLeaves);
        leaveSummary.setLeavesTaken(leaveTaken);
        leaveSummary.setEmployeeId(leaveTransaction.getEmployeeId());
        leaveSummary.setLeaveTypeId(leaveTransaction.getLeaveTypeId());
        leaveSummary.setPeriodStartDt(DateTimeUtils.getFirstDayOfYear());
        leaveSummary.setPeriodEndDt(DateTimeUtils.getLastDayOfYear());
        leaveSummary.setLeaveOpen(leaveOpen);
        leaveSummary.setLeaveCredit(leaveCredit);

        return leaveSummary;
    }
}
