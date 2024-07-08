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
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.time.LocalDateTime;

public class LeaveMapperTest {

    private LeaveMapper leaveMapperUnderTest;

    @Before
    public void setUp() {
        leaveMapperUnderTest = new LeaveMapper();
    }

    @Test
    public void testLeaveEntityToModel() {
        final LeaveTransaction leaveTransaction = new LeaveTransaction();
        leaveTransaction.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTransaction.setLeaveTypeId(leaveTypeId);
        leaveTransaction.setStartDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setEndDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setNoOfDays(0.0);
        leaveTransaction.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction.setReason("reason");
        leaveTransaction.setLeaveForStartDt(EnumLeavePeriod.HALF_DAY);
        leaveTransaction.setLeaveForEndDt(EnumLeavePeriod.HALF_DAY);
        leaveTransaction.setReportingManager(0L);
        leaveTransaction.setStatus(EnumStatus.ACTIVE);
        leaveTransaction.setCreatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setCreatedBy("createdBy");
        leaveTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setLastUpdatedBy("createdBy");

       leaveMapperUnderTest.leaveEntityToModel(leaveTransaction);
    }

    @Test
    public void testMapLeaveSummaryEntityNew() {
        final LeaveType leaveType = new LeaveType();
        leaveType.setId(0L);
        leaveType.setName("name");
        leaveType.setCode("code");
        leaveType.setDescription("description");
        leaveType.setLeavesAllowed(0.0);

        leaveMapperUnderTest.mapLeaveSummaryEntityNew(leaveType, 0L);
    }

    @Test
    public void testLeaveModelToLeaveEntity() {

        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setLeaveTypeId("1");
        applyLeaveRequest.setStartDt("2024-07-08 00:00:00");
        applyLeaveRequest.setEndDt("2024-07-08 00:00:00");
        applyLeaveRequest.setNoOfDays("1");
        applyLeaveRequest.setReason("reason");
        applyLeaveRequest.setLeaveForStartDt("FULL_DAY");
        applyLeaveRequest.setLeaveForEndDt("FULL_DAY");

        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("101");
        employeeDetail.setFirstName("Aakash");
        employeeDetail.setLastName("B");
        employeeDetail.setOfficialEmail("aakash.b@aaludra.com");
        employeeDetail.setApproverId(employeeDetail);

        final LeaveType leaveType = new LeaveType();
        leaveType.setId(0L);
        leaveType.setName("name");
        leaveType.setCode("code");
        leaveType.setDescription("description");
        leaveType.setLeavesAllowed(0.0);

        leaveMapperUnderTest.leaveModelToLeaveEntity(applyLeaveRequest, employeeDetail,
                leaveType);

    }

    @Test
    public void testMapLeaveSummaryEntity() {

        final LeaveTransaction leaveTransaction = new LeaveTransaction();
        leaveTransaction.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTransaction.setLeaveTypeId(leaveTypeId);
        leaveTransaction.setStartDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setEndDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setNoOfDays(0.0);
        leaveTransaction.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction.setReason("reason");
        leaveTransaction.setLeaveForStartDt(EnumLeavePeriod.HALF_DAY);
        leaveTransaction.setLeaveForEndDt(EnumLeavePeriod.HALF_DAY);
        leaveTransaction.setReportingManager(0L);
        leaveTransaction.setStatus(EnumStatus.ACTIVE);
        leaveTransaction.setCreatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setCreatedBy("createdBy");
        leaveTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setLastUpdatedBy("createdBy");

        leaveMapperUnderTest.mapLeaveSummaryEntity(leaveTransaction, 0.0, 0.0, 0L, 0.0,
                0.0);

    }
}
