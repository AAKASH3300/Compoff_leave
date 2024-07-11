package com.itime.compoff.primary.entity;

import com.itime.compoff.enumeration.EnumCompOffPeriod;
import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumCompOffUsageStatus;
import com.itime.compoff.enumeration.EnumStatus;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Time;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompOffTransactionTest {

    private CompOffTransaction compOffTransaction;

    @BeforeEach
    public void setUp() {
        compOffTransaction = new CompOffTransaction();
    }

    @Test
    public void testGettersAndSetters() {
        long id = 1L;
        long employeeId = 12345L;
        Timestamp requestedDt = new Timestamp(System.currentTimeMillis());
        Timestamp expiryDate = new Timestamp(System.currentTimeMillis());
        Time punchInTime = new Time(System.currentTimeMillis());
        Time punchOutTime = new Time(System.currentTimeMillis());
        double workHours = 8.0;
        String reason = "Personal";
        long approverId = 67890L;
        String approverRemarks = "Approved";
        String cancellationReason = "No longer needed";
        EnumCompOffPeriod requestedFor = EnumCompOffPeriod.FULL_DAY;
        EnumCompOffPeriod approvedFor = EnumCompOffPeriod.HALF_DAY;
        EnumCompOffTransactionStatus transactionStatus = EnumCompOffTransactionStatus.APPROVED;
        EnumCompOffUsageStatus compOffUsageStatus = EnumCompOffUsageStatus.USED;
        LeaveTransaction leaveTransaction = new LeaveTransaction();
        EnumStatus status = EnumStatus.ACTIVE;
        Timestamp createdDt = new Timestamp(System.currentTimeMillis());
        String createdBy = "UserA";
        Timestamp lastUpdatedDt = new Timestamp(System.currentTimeMillis());
        String lastUpdatedBy = "UserB";

        compOffTransaction.setId(id);
        compOffTransaction.setEmployeeId(employeeId);
        compOffTransaction.setRequestedDate(requestedDt);
        compOffTransaction.setExpiryDate(expiryDate);
        compOffTransaction.setPunchInTime(punchInTime);
        compOffTransaction.setPunchOutTime(punchOutTime);
        compOffTransaction.setWorkHours(workHours);
        compOffTransaction.setReason(reason);
        compOffTransaction.setApproverId(approverId);
        compOffTransaction.setApproverRemarks(approverRemarks);
        compOffTransaction.setCancellationReason(cancellationReason);
        compOffTransaction.setRequestedFor(requestedFor);
        compOffTransaction.setApprovedFor(approvedFor);
        compOffTransaction.setTransactionStatus(transactionStatus);
        compOffTransaction.setCompOffUsageStatus(compOffUsageStatus);
        compOffTransaction.setLeaveTransaction(leaveTransaction);
        compOffTransaction.setStatus(status);
        compOffTransaction.setCreatedDt(createdDt);
        compOffTransaction.setCreatedBy(createdBy);
        compOffTransaction.setLastUpdatedDt(lastUpdatedDt);
        compOffTransaction.setLastUpdatedBy(lastUpdatedBy);

        assertEquals(id, compOffTransaction.getId());
        assertEquals(employeeId, compOffTransaction.getEmployeeId());
        assertEquals(requestedDt, compOffTransaction.getRequestedDate());
        assertEquals(expiryDate, compOffTransaction.getExpiryDate());
        assertEquals(punchInTime, compOffTransaction.getPunchInTime());
        assertEquals(punchOutTime, compOffTransaction.getPunchOutTime());
        assertEquals(workHours, compOffTransaction.getWorkHours());
        assertEquals(reason, compOffTransaction.getReason());
        assertEquals(approverId, compOffTransaction.getApproverId());
        assertEquals(approverRemarks, compOffTransaction.getApproverRemarks());
        assertEquals(cancellationReason, compOffTransaction.getCancellationReason());
        assertEquals(requestedFor, compOffTransaction.getRequestedFor());
        assertEquals(approvedFor, compOffTransaction.getApprovedFor());
        assertEquals(transactionStatus, compOffTransaction.getTransactionStatus());
        assertEquals(compOffUsageStatus, compOffTransaction.getCompOffUsageStatus());
        assertEquals(leaveTransaction, compOffTransaction.getLeaveTransaction());
        assertEquals(status, compOffTransaction.getStatus());
        assertEquals(createdDt, compOffTransaction.getCreatedDt());
        assertEquals(createdBy, compOffTransaction.getCreatedBy());
        assertEquals(lastUpdatedDt, compOffTransaction.getLastUpdatedDt());
        assertEquals(lastUpdatedBy, compOffTransaction.getLastUpdatedBy());
    }
}
