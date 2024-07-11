package com.itime.compoff.primary.entity;

import com.itime.compoff.enumeration.EnumStatus;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

public class LeaveTypeTest {

    @Test
    public void testLeaveTypeEntity() {
        LeaveType leaveType = new LeaveType();

        leaveType.setId(1L);
        leaveType.setName("Sick Leave");
        leaveType.setCode("SL");
        leaveType.setDescription("Leave for sickness");
        leaveType.setLeavesAllowed(12.0);
        leaveType.setLeaveTypeStatus(EnumStatus.ACTIVE);
        leaveType.setStatus(EnumStatus.ACTIVE);
        leaveType.setCreatedDt(Timestamp.valueOf("2024-01-01 00:00:00"));
        leaveType.setCreatedBy("admin");
        leaveType.setLastUpdatedDt(Timestamp.valueOf("2024-01-02 00:00:00"));
        leaveType.setLastUpdatedBy("admin");
        leaveType.setClubWeekEnd(1);
        leaveType.setClubHoliday(0);

        assertThat(leaveType.getId()).isEqualTo(1L);
        assertThat(leaveType.getName()).isEqualTo("Sick Leave");
        assertThat(leaveType.getCode()).isEqualTo("SL");
        assertThat(leaveType.getDescription()).isEqualTo("Leave for sickness");
        assertThat(leaveType.getLeavesAllowed()).isEqualTo(12.0);
        assertThat(leaveType.getLeaveTypeStatus()).isEqualTo(EnumStatus.ACTIVE);
        assertThat(leaveType.getStatus()).isEqualTo(EnumStatus.ACTIVE);
        assertThat(leaveType.getCreatedDt()).isEqualTo(Timestamp.valueOf("2024-01-01 00:00:00"));
        assertThat(leaveType.getCreatedBy()).isEqualTo("admin");
        assertThat(leaveType.getLastUpdatedDt()).isEqualTo(Timestamp.valueOf("2024-01-02 00:00:00"));
        assertThat(leaveType.getLastUpdatedBy()).isEqualTo("admin");
        assertThat(leaveType.getClubWeekEnd()).isEqualTo(1);
        assertThat(leaveType.getClubHoliday()).isEqualTo(0);
    }

}
