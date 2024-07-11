package com.itime.compoff.primary.entity;

import com.itime.compoff.enumeration.EnumLeavePeriod;
import com.itime.compoff.enumeration.EnumLeaveStatus;
import com.itime.compoff.enumeration.EnumStatus;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

public class LeaveTransactionTest {

    @Test
     void testLeaveTransactionEntity() {

        LeaveTransaction leaveTransaction = new LeaveTransaction();

        LeaveType leaveType = new LeaveType();
        leaveType.setId(1L);

        leaveTransaction.setId(1L);
        leaveTransaction.setEmployeeId(1001L);
        leaveTransaction.setLeaveTypeId(leaveType);
        leaveTransaction.setStartDt(Timestamp.valueOf("2024-01-01 09:00:00"));
        leaveTransaction.setEndDt(Timestamp.valueOf("2024-01-02 18:00:00"));
        leaveTransaction.setNoOfDays(2.0);
        leaveTransaction.setLeaveStatus(EnumLeaveStatus.APPROVED);
        leaveTransaction.setReason("Vacation");
        leaveTransaction.setLeaveForStartDt(EnumLeavePeriod.FULL_DAY);
        leaveTransaction.setLeaveForEndDt(EnumLeavePeriod.FULL_DAY);
        leaveTransaction.setCancellationReason("Change of plans");
        leaveTransaction.setReportingManager(2001L);
        leaveTransaction.setApproverRemarks("Approved");
        leaveTransaction.setStatus(EnumStatus.ACTIVE);
        leaveTransaction.setCreatedDt(Timestamp.valueOf("2023-12-01 00:00:00"));
        leaveTransaction.setCreatedBy("admin");
        leaveTransaction.setLastUpdatedDt(Timestamp.valueOf("2023-12-15 00:00:00"));
        leaveTransaction.setLastUpdatedBy("admin");

        assertThat(leaveTransaction.getId()).isEqualTo(1L);
        assertThat(leaveTransaction.getEmployeeId()).isEqualTo(1001L);
        assertThat(leaveTransaction.getLeaveTypeId()).isEqualTo(leaveType);
        assertThat(leaveTransaction.getStartDt()).isEqualTo(Timestamp.valueOf("2024-01-01 09:00:00"));
        assertThat(leaveTransaction.getEndDt()).isEqualTo(Timestamp.valueOf("2024-01-02 18:00:00"));
        assertThat(leaveTransaction.getNoOfDays()).isEqualTo(2.0);
        assertThat(leaveTransaction.getLeaveStatus()).isEqualTo(EnumLeaveStatus.APPROVED);
        assertThat(leaveTransaction.getReason()).isEqualTo("Vacation");
        assertThat(leaveTransaction.getLeaveForStartDt()).isEqualTo(EnumLeavePeriod.FULL_DAY);
        assertThat(leaveTransaction.getLeaveForEndDt()).isEqualTo(EnumLeavePeriod.FULL_DAY);
        assertThat(leaveTransaction.getCancellationReason()).isEqualTo("Change of plans");
        assertThat(leaveTransaction.getReportingManager()).isEqualTo(2001L);
        assertThat(leaveTransaction.getApproverRemarks()).isEqualTo("Approved");
        assertThat(leaveTransaction.getStatus()).isEqualTo(EnumStatus.ACTIVE);
        assertThat(leaveTransaction.getCreatedDt()).isEqualTo(Timestamp.valueOf("2023-12-01 00:00:00"));
        assertThat(leaveTransaction.getCreatedBy()).isEqualTo("admin");
        assertThat(leaveTransaction.getLastUpdatedDt()).isEqualTo(Timestamp.valueOf("2023-12-15 00:00:00"));
        assertThat(leaveTransaction.getLastUpdatedBy()).isEqualTo("admin");
    }
}
