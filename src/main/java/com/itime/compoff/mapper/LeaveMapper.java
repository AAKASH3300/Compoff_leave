package com.itime.compoff.mapper;

import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import com.itime.compoff.primary.entity.LeaveSummary;
import com.itime.compoff.primary.entity.LeaveTransaction;
import com.itime.compoff.primary.entity.LeaveType;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.utils.DateTimeUtils;
import org.springframework.stereotype.Component;

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
        applyLeaveResponse.setLeaveForStartDt(String.valueOf(leaveTransaction.getLeaveForStartDt()));
        applyLeaveResponse.setLeaveForEndDt(String.valueOf(leaveTransaction.getLeaveForEndDt()));
        applyLeaveResponse
                .setReportingManager(String.valueOf(leaveTransaction.getReportingManager()));
        applyLeaveResponse.setStatus(String.valueOf(leaveTransaction.getStatus()));
        applyLeaveResponse.setAppliedOn(String.valueOf(leaveTransaction.getCreatedDt()));
        applyLeaveResponse.setLastUpdatedDt(String.valueOf(leaveTransaction.getLastUpdatedDt()));
        applyLeaveResponse.setLeaveTypeName(leaveTransaction.getLeaveTypeId().getName());
        applyLeaveResponse.setCreatedByEmpId(String.valueOf(leaveTransaction.getEmployeeId()));

        return applyLeaveResponse;
    }

    public LeaveSummary mapLeaveSummaryEntityNew(long id) {
        return null;
    }

    public LeaveTransaction leaveModelToLeaveEntity(LeaveApplyRequest applyLeaverequest, EmployeeDetail employeeDetail, LeaveType leaveType) {
        return null;
    }
}
