package com.itime.compoff.mapper;


import com.itime.compoff.enumeration.EnumCompOffPeriod;
import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumCompOffUsageStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.utils.DateTimeUtils;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Component
public class CompOffMapper {


    public CompOffTransaction mapApplyRequestToEntity(EmployeeDetail employeeDetails, CompOffApplyRequest compOffApplyRequest) {
        CompOffTransaction compOffTransaction = new CompOffTransaction();

        compOffTransaction.setEmployeeId(employeeDetails.getId());
        compOffTransaction.setRequestedDate(Timestamp.valueOf(compOffApplyRequest.getRequestedDate()));
        LocalDateTime requestedDate = compOffTransaction.getRequestedDate().toLocalDateTime();
        compOffTransaction.setExpiryDate(Timestamp.valueOf(requestedDate.plusDays(90)));
        compOffTransaction.setApproverId(employeeDetails.getApproverId().getId());
        compOffTransaction.setPunchInTime(Time.valueOf(compOffApplyRequest.getPunchIn()));
        compOffTransaction.setPunchOutTime(Time.valueOf(compOffApplyRequest.getPunchOut()));
        compOffTransaction.setWorkHours(Double.valueOf(compOffApplyRequest.getWorkHours()));
        compOffTransaction.setReason(compOffApplyRequest.getReason());
        compOffTransaction.setApproverRemarks(compOffApplyRequest.getReason());
        compOffTransaction.setRequestedFor(EnumCompOffPeriod.valueOf(compOffApplyRequest.getRequestedFor()));
        compOffTransaction.setApprovedFor(EnumCompOffPeriod.valueOf(compOffApplyRequest.getRequestedFor()));
        compOffTransaction.setTransactionStatus(EnumCompOffTransactionStatus.PENDING);
        compOffTransaction.setCompOffUsageStatus(EnumCompOffUsageStatus.REQUESTED);
        compOffTransaction.setStatus(EnumStatus.ACTIVE);
        compOffTransaction.setCreatedBy(employeeDetails.getFirstName());
        compOffTransaction.setLastUpdatedBy(employeeDetails.getFirstName());
        compOffTransaction.setCreatedDt(DateTimeUtils.getCurrentTimeStamp());
        compOffTransaction.setLastUpdatedDt(DateTimeUtils.getCurrentTimeStamp());

        return compOffTransaction;
    }


}