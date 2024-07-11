package com.itime.compoff.primary.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

public class LeaveSummaryTest {

    @Test
    void testLeaveSummaryEntity() {

        LeaveSummary leaveSummary = new LeaveSummary();

        LeaveType leaveType = new LeaveType();
        leaveType.setId(1L);

        leaveSummary.setId(1L);
        leaveSummary.setEmployeeId(1001L);
        leaveSummary.setLeaveTypeId(leaveType);
        leaveSummary.setPeriodStartDt(Timestamp.valueOf("2024-01-01 00:00:00"));
        leaveSummary.setPeriodEndDt(Timestamp.valueOf("2024-12-31 23:59:59"));
        leaveSummary.setLeavesTaken(5.0);
        leaveSummary.setLeavesAvailable(10.0);
        leaveSummary.setLeaveOpen(2.0);
        leaveSummary.setLeaveCredit(1.0);

        assertThat(leaveSummary.getId()).isEqualTo(1L);
        assertThat(leaveSummary.getEmployeeId()).isEqualTo(1001L);
        assertThat(leaveSummary.getLeaveTypeId()).isEqualTo(leaveType);
        assertThat(leaveSummary.getPeriodStartDt()).isEqualTo(Timestamp.valueOf("2024-01-01 00:00:00"));
        assertThat(leaveSummary.getPeriodEndDt()).isEqualTo(Timestamp.valueOf("2024-12-31 23:59:59"));
        assertThat(leaveSummary.getLeavesTaken()).isEqualTo(5.0);
        assertThat(leaveSummary.getLeavesAvailable()).isEqualTo(10.0);
        assertThat(leaveSummary.getLeaveOpen()).isEqualTo(2.0);
        assertThat(leaveSummary.getLeaveCredit()).isEqualTo(1.0);
    }

}
