package com.itime.compoff.service.impl;

import com.itime.compoff.enumeration.*;
import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
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
import com.itime.compoff.validation.BusinessValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LeaveTransactionServiceImplTest {

    @Mock
    private LeaveMapper mockLeaveMapper;
    @Mock
    private LeaveTypeRepo mockLeaveTypeRepo;
    @Mock
    private LeaveTransactionRepo mockLeaveTransactionRepo;
    @Mock
    private CompOffTransactionRepo mockCompOffTransactionRepo;
    @Mock
    private LeaveSummaryRepo mockLeaveSummaryRepo;
    @Mock
    private BusinessValidationService mockBusinessValidationService;

    private LeaveTransactionServiceImpl leaveTransactionServiceImplUnderTest;

    @Before
    public void setUp() {
        leaveTransactionServiceImplUnderTest = new LeaveTransactionServiceImpl();
        leaveTransactionServiceImplUnderTest.leaveMapper = mockLeaveMapper;
        leaveTransactionServiceImplUnderTest.leaveTypeRepo = mockLeaveTypeRepo;
        leaveTransactionServiceImplUnderTest.leaveTransactionRepo = mockLeaveTransactionRepo;
        leaveTransactionServiceImplUnderTest.compOffTransactionRepo = mockCompOffTransactionRepo;
        leaveTransactionServiceImplUnderTest.leaveSummaryRepo = mockLeaveSummaryRepo;
        leaveTransactionServiceImplUnderTest.businessValidationService = mockBusinessValidationService;
    }

    @Test
    public void testSaveLeave() throws Exception {
        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("1");
        applyLeaveRequest.setLeaveTypeId(String.valueOf(1));
        applyLeaveRequest.setStartDt("2024-07-08 00:00:00");
        applyLeaveRequest.setEndDt("2024-07-08 00:00:00");
        applyLeaveRequest.setNoOfDays("1");

        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("101");
        employeeDetail.setFirstName("Aakash");
        employeeDetail.setLastName("B");
        employeeDetail.setOfficialEmail("aakash.b@aaludra.com");
        employeeDetail.setApproverId(employeeDetail);
        when(mockBusinessValidationService.findEmployeeDetail(1L)).thenReturn(employeeDetail);

        final LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(1);
        leaveType1.setName("name");
        leaveType1.setCode("code");
        leaveType1.setDescription("description");
        leaveType1.setLeavesAllowed(0.0);
        final Optional<LeaveType> leaveType = Optional.of(leaveType1);
        when(mockLeaveTypeRepo.findByIdAndStatus(1L, EnumStatus.ACTIVE)).thenReturn(leaveType);

        final LeaveTransaction leaveTransaction = new LeaveTransaction();
        leaveTransaction.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(1);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction.setLeaveTypeId(leaveTypeId);
        leaveTransaction.setNoOfDays(0.0);
        leaveTransaction.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction.setReportingManager(0L);
        leaveTransaction.setApproverRemarks("comment");
        leaveTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setLastUpdatedBy("firstName");
        when(mockLeaveMapper.leaveModelToLeaveEntity(any(LeaveApplyRequest.class), any(EmployeeDetail.class),
                any(LeaveType.class))).thenReturn(leaveTransaction);

        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId1 = new LeaveType();
        leaveTypeId1.setId(1);
        leaveTypeId1.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId1);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        when(mockLeaveTransactionRepo.save(any(LeaveTransaction.class))).thenReturn(leaveTransaction1);

        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(1L);
        leaveSummary1.setLeavesTaken(0.0);
        leaveSummary1.setLeavesAvailable(0.0);
        leaveSummary1.setLeaveOpen(0.0);
        leaveSummary1.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary = Optional.of(leaveSummary1);
        when(mockLeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(any(LeaveType.class), eq(1L)))
                .thenReturn(leaveSummary);

        final CompOffTransaction compOffTransaction = new CompOffTransaction();
        compOffTransaction.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction2 = new LeaveTransaction();
        leaveTransaction2.setEmployeeId(0L);
        final LeaveType leaveTypeId2 = new LeaveType();
        leaveTypeId2.setId(1);
        leaveTypeId2.setLeavesAllowed(0.0);
        leaveTransaction2.setLeaveTypeId(leaveTypeId2);
        leaveTransaction2.setNoOfDays(0.0);
        leaveTransaction2.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction2.setReportingManager(0L);
        leaveTransaction2.setApproverRemarks("comment");
        leaveTransaction2.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction2.setLastUpdatedBy("firstName");
        compOffTransaction.setLeaveTransaction(leaveTransaction2);
        compOffTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> compOffTransactions = List.of(compOffTransaction);
        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(
                0L, Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)), EnumCompOffTransactionStatus.APPROVED,
                List.of(EnumCompOffUsageStatus.AVAILABLE), EnumStatus.ACTIVE)).thenReturn(compOffTransactions);

        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction1.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction3 = new LeaveTransaction();
        leaveTransaction3.setEmployeeId(0L);
        final LeaveType leaveTypeId3 = new LeaveType();
        leaveTypeId3.setId(1);
        leaveTypeId3.setLeavesAllowed(0.0);
        leaveTransaction3.setLeaveTypeId(leaveTypeId3);
        leaveTransaction3.setNoOfDays(0.0);
        leaveTransaction3.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction3.setReportingManager(0L);
        leaveTransaction3.setApproverRemarks("comment");
        leaveTransaction3.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction3.setLastUpdatedBy("firstName");
        compOffTransaction1.setLeaveTransaction(leaveTransaction3);
        compOffTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> compOffTransactions1 = List.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc(
                eq(0L), eq(EnumCompOffTransactionStatus.APPROVED), eq(List.of(EnumCompOffUsageStatus.AVAILABLE)),
                eq(EnumStatus.ACTIVE), any(LeaveTransaction.class))).thenReturn(compOffTransactions1);

        final LeaveResponse leaveResponse = new LeaveResponse();
        leaveResponse.setEmployeeId("2");
        leaveResponse.setLeaveTypeId("1");
        leaveResponse.setStartDt("2024-07-08 00:00:00");
        leaveResponse.setEndDt("2024-07-08 00:00:00");
        leaveResponse.setNoOfDays("2");
        when(mockLeaveMapper.leaveEntityToModel(any(LeaveTransaction.class))).thenReturn(leaveResponse);

        leaveTransactionServiceImplUnderTest.saveLeave(applyLeaveRequest,"101");

        verify(mockBusinessValidationService).duplicateLeaveCheck(any(EmployeeDetail.class),
                any(LeaveApplyRequest.class));
        verify(mockBusinessValidationService).validateCompOffLeave(any(LeaveType.class), any(LeaveApplyRequest.class));

        final LeaveSummary leaveSummary3 = new LeaveSummary();
        leaveSummary3.setId(1L);
        leaveSummary3.setLeavesTaken(0.0);
        leaveSummary3.setLeavesAvailable(0.0);
        leaveSummary3.setLeaveOpen(0.0);
        leaveSummary3.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary2 = Optional.of(leaveSummary3);
        verify(mockBusinessValidationService).validateAvailableLeave(leaveSummary2, 0.0);

        final CompOffTransaction compOffTransaction2 = new CompOffTransaction();
        compOffTransaction2.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction2.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction4 = new LeaveTransaction();
        leaveTransaction4.setEmployeeId(0L);
        final LeaveType leaveTypeId4 = new LeaveType();
        leaveTypeId4.setId(0L);
        leaveTypeId4.setLeavesAllowed(0.0);
        leaveTransaction4.setLeaveTypeId(leaveTypeId4);
        leaveTransaction4.setNoOfDays(0.0);
        leaveTransaction4.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction4.setReportingManager(0L);
        leaveTransaction4.setApproverRemarks("comment");
        leaveTransaction4.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction4.setLastUpdatedBy("firstName");
        compOffTransaction2.setLeaveTransaction(leaveTransaction4);
        compOffTransaction2.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction2.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> entities = List.of(compOffTransaction2);
        verify(mockCompOffTransactionRepo).saveAll(entities);
    }

    @Test
    public void testSaveLeave_BusinessValidationServiceFindEmployeeDetailThrowsDataNotFoundException() throws Exception {
        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("1");
        applyLeaveRequest.setLeaveTypeId("1");
        applyLeaveRequest.setStartDt("2024-07-08 00:00:00");
        applyLeaveRequest.setEndDt("2024-07-08 00:00:00");
        applyLeaveRequest.setNoOfDays("1");

        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenThrow(DataNotFoundException.class);

        assertThrows(DataNotFoundException.class,
                () -> leaveTransactionServiceImplUnderTest.saveLeave(applyLeaveRequest, "createdByEmpId"));
    }

    @Test
    public void testSaveLeave_LeaveTypeRepoReturnsAbsent() throws Exception {

        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("2");
        applyLeaveRequest.setLeaveTypeId("1");
        applyLeaveRequest.setStartDt("2024-07-08 00:00:00");
        applyLeaveRequest.setEndDt("2024-07-08 00:00:00");
        applyLeaveRequest.setNoOfDays("1");

        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("101");
        employeeDetail.setFirstName("Aakash");
        employeeDetail.setLastName("B");
        employeeDetail.setOfficialEmail("aakash.b@aaludra.com");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        when(mockLeaveTypeRepo.findByIdAndStatus(1L, EnumStatus.ACTIVE)).thenReturn(Optional.empty());

        assertThrows(DataNotFoundException.class,
                () -> leaveTransactionServiceImplUnderTest.saveLeave(applyLeaveRequest, "createdByEmpId"));
    }

    @Test
    public void testSaveLeave_BusinessValidationServiceDuplicateLeaveCheckThrowsCommonException() throws Exception {
        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("1");
        applyLeaveRequest.setLeaveTypeId("1");
        applyLeaveRequest.setStartDt("2024-07-08 00:00:00");
        applyLeaveRequest.setEndDt("2024-07-08 00:00:00");
        applyLeaveRequest.setNoOfDays("1");

        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("101");
        employeeDetail.setFirstName("Aakash");
        employeeDetail.setLastName("B");
        employeeDetail.setOfficialEmail("aakash.b@aaludra.com");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        final LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(0L);
        leaveType1.setName("name");
        leaveType1.setCode("code");
        leaveType1.setDescription("description");
        leaveType1.setLeavesAllowed(0.0);
        final Optional<LeaveType> leaveType = Optional.of(leaveType1);
        when(mockLeaveTypeRepo.findByIdAndStatus(1L, EnumStatus.ACTIVE)).thenReturn(leaveType);

        doThrow(CommonException.class).when(mockBusinessValidationService).duplicateLeaveCheck(
                any(EmployeeDetail.class), any(LeaveApplyRequest.class));

        assertThrows(CommonException.class,
                () -> leaveTransactionServiceImplUnderTest.saveLeave(applyLeaveRequest, "createdByEmpId"));
    }

    @Test
    public void testSaveLeave_BusinessValidationServiceValidateCompOffLeaveThrowsCommonException() throws Exception {

        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("1");
        applyLeaveRequest.setLeaveTypeId("1");
        applyLeaveRequest.setStartDt("2024-07-08 00:00:00");
        applyLeaveRequest.setEndDt("2024-07-08 00:00:00");
        applyLeaveRequest.setNoOfDays("1");

        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("101");
        employeeDetail.setFirstName("Aakash");
        employeeDetail.setLastName("B");
        employeeDetail.setOfficialEmail("aakash.b@aaludra.com");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        final LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(0L);
        leaveType1.setName("name");
        leaveType1.setCode("code");
        leaveType1.setDescription("description");
        leaveType1.setLeavesAllowed(0.0);
        final Optional<LeaveType> leaveType = Optional.of(leaveType1);
        when(mockLeaveTypeRepo.findByIdAndStatus(1L, EnumStatus.ACTIVE)).thenReturn(leaveType);

        doThrow(CommonException.class).when(mockBusinessValidationService).validateCompOffLeave(any(LeaveType.class),
                any(LeaveApplyRequest.class));

        assertThrows(CommonException.class,
                () -> leaveTransactionServiceImplUnderTest.saveLeave(applyLeaveRequest, "101"));
        verify(mockBusinessValidationService).duplicateLeaveCheck(any(EmployeeDetail.class),
                any(LeaveApplyRequest.class));
    }

    @Test
    public void testSaveLeave_LeaveSummaryRepoFindByLeaveTypeIdAndEmployeeIdReturnsAbsent() throws Exception {
        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("employeeId");
        applyLeaveRequest.setLeaveTypeId("leaveTypeId");
        applyLeaveRequest.setStartDt("startDt");
        applyLeaveRequest.setEndDt("endDt");
        applyLeaveRequest.setNoOfDays("noOfDays");

        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("empId");
        employeeDetail.setFirstName("firstName");
        employeeDetail.setLastName("lastName");
        employeeDetail.setOfficialEmail("officialEmail");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        final LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(0L);
        leaveType1.setName("name");
        leaveType1.setCode("code");
        leaveType1.setDescription("description");
        leaveType1.setLeavesAllowed(0.0);
        final Optional<LeaveType> leaveType = Optional.of(leaveType1);
        when(mockLeaveTypeRepo.findByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(leaveType);

        final LeaveTransaction leaveTransaction = new LeaveTransaction();
        leaveTransaction.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction.setLeaveTypeId(leaveTypeId);
        leaveTransaction.setNoOfDays(0.0);
        leaveTransaction.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction.setReportingManager(0L);
        leaveTransaction.setApproverRemarks("comment");
        leaveTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setLastUpdatedBy("firstName");
        when(mockLeaveMapper.leaveModelToLeaveEntity(any(LeaveApplyRequest.class), any(EmployeeDetail.class),
                any(LeaveType.class))).thenReturn(leaveTransaction);

        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId1 = new LeaveType();
        leaveTypeId1.setId(0L);
        leaveTypeId1.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId1);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        when(mockLeaveTransactionRepo.save(any(LeaveTransaction.class))).thenReturn(leaveTransaction1);

        when(mockLeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(any(LeaveType.class), eq(0L)))
                .thenReturn(Optional.empty());

        final LeaveSummary leaveSummary = new LeaveSummary();
        leaveSummary.setId(0L);
        leaveSummary.setLeavesTaken(0.0);
        leaveSummary.setLeavesAvailable(0.0);
        leaveSummary.setLeaveOpen(0.0);
        leaveSummary.setLeaveCredit(0.0);
        when(mockLeaveMapper.mapLeaveSummaryEntityNew(any(LeaveType.class), eq(0L))).thenReturn(leaveSummary);

        final CompOffTransaction compOffTransaction = new CompOffTransaction();
        compOffTransaction.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction2 = new LeaveTransaction();
        leaveTransaction2.setEmployeeId(0L);
        final LeaveType leaveTypeId2 = new LeaveType();
        leaveTypeId2.setId(0L);
        leaveTypeId2.setLeavesAllowed(0.0);
        leaveTransaction2.setLeaveTypeId(leaveTypeId2);
        leaveTransaction2.setNoOfDays(0.0);
        leaveTransaction2.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction2.setReportingManager(0L);
        leaveTransaction2.setApproverRemarks("comment");
        leaveTransaction2.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction2.setLastUpdatedBy("firstName");
        compOffTransaction.setLeaveTransaction(leaveTransaction2);
        compOffTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> compOffTransactions = List.of(compOffTransaction);
        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(
                0L, Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)), EnumCompOffTransactionStatus.APPROVED,
                List.of(EnumCompOffUsageStatus.AVAILABLE), EnumStatus.ACTIVE)).thenReturn(compOffTransactions);

        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction1.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction3 = new LeaveTransaction();
        leaveTransaction3.setEmployeeId(0L);
        final LeaveType leaveTypeId3 = new LeaveType();
        leaveTypeId3.setId(0L);
        leaveTypeId3.setLeavesAllowed(0.0);
        leaveTransaction3.setLeaveTypeId(leaveTypeId3);
        leaveTransaction3.setNoOfDays(0.0);
        leaveTransaction3.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction3.setReportingManager(0L);
        leaveTransaction3.setApproverRemarks("comment");
        leaveTransaction3.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction3.setLastUpdatedBy("firstName");
        compOffTransaction1.setLeaveTransaction(leaveTransaction3);
        compOffTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> compOffTransactions1 = List.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc(
                eq(0L), eq(EnumCompOffTransactionStatus.APPROVED), eq(List.of(EnumCompOffUsageStatus.AVAILABLE)),
                eq(EnumStatus.ACTIVE), any(LeaveTransaction.class))).thenReturn(compOffTransactions1);

        final LeaveResponse leaveResponse = new LeaveResponse();
        leaveResponse.setEmployeeId("1");
        leaveResponse.setLeaveTypeId("1");
        leaveResponse.setStartDt("2024-07-08 00:00:00");
        leaveResponse.setEndDt("2024-07-08 00:00:00");
        leaveResponse.setNoOfDays("1");
        when(mockLeaveMapper.leaveEntityToModel(any(LeaveTransaction.class))).thenReturn(leaveResponse);

        final LeaveResponse result = leaveTransactionServiceImplUnderTest.saveLeave(applyLeaveRequest,
                "createdByEmpId");

        verify(mockBusinessValidationService).duplicateLeaveCheck(any(EmployeeDetail.class),
                any(LeaveApplyRequest.class));
        verify(mockBusinessValidationService).validateCompOffLeave(any(LeaveType.class), any(LeaveApplyRequest.class));
        verify(mockLeaveSummaryRepo).save(any(LeaveSummary.class));

        final CompOffTransaction compOffTransaction2 = new CompOffTransaction();
        compOffTransaction2.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction2.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction4 = new LeaveTransaction();
        leaveTransaction4.setEmployeeId(0L);
        final LeaveType leaveTypeId4 = new LeaveType();
        leaveTypeId4.setId(0L);
        leaveTypeId4.setLeavesAllowed(0.0);
        leaveTransaction4.setLeaveTypeId(leaveTypeId4);
        leaveTransaction4.setNoOfDays(0.0);
        leaveTransaction4.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction4.setReportingManager(0L);
        leaveTransaction4.setApproverRemarks("comment");
        leaveTransaction4.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction4.setLastUpdatedBy("firstName");
        compOffTransaction2.setLeaveTransaction(leaveTransaction4);
        compOffTransaction2.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction2.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> entities = List.of(compOffTransaction2);
        verify(mockCompOffTransactionRepo).saveAll(entities);
    }

    @Test
    public void testSaveLeave_BusinessValidationServiceValidateAvailableLeaveThrowsCommonException() throws Exception {
        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("employeeId");
        applyLeaveRequest.setLeaveTypeId("leaveTypeId");
        applyLeaveRequest.setStartDt("startDt");
        applyLeaveRequest.setEndDt("endDt");
        applyLeaveRequest.setNoOfDays("noOfDays");

        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("empId");
        employeeDetail.setFirstName("firstName");
        employeeDetail.setLastName("lastName");
        employeeDetail.setOfficialEmail("officialEmail");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        final LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(0L);
        leaveType1.setName("name");
        leaveType1.setCode("code");
        leaveType1.setDescription("description");
        leaveType1.setLeavesAllowed(0.0);
        final Optional<LeaveType> leaveType = Optional.of(leaveType1);
        when(mockLeaveTypeRepo.findByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(leaveType);

        final LeaveTransaction leaveTransaction = new LeaveTransaction();
        leaveTransaction.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction.setLeaveTypeId(leaveTypeId);
        leaveTransaction.setNoOfDays(0.0);
        leaveTransaction.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction.setReportingManager(0L);
        leaveTransaction.setApproverRemarks("comment");
        leaveTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setLastUpdatedBy("firstName");
        when(mockLeaveMapper.leaveModelToLeaveEntity(any(LeaveApplyRequest.class), any(EmployeeDetail.class),
                any(LeaveType.class))).thenReturn(leaveTransaction);

        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId1 = new LeaveType();
        leaveTypeId1.setId(0L);
        leaveTypeId1.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId1);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        when(mockLeaveTransactionRepo.save(any(LeaveTransaction.class))).thenReturn(leaveTransaction1);

        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(0L);
        leaveSummary1.setLeavesTaken(0.0);
        leaveSummary1.setLeavesAvailable(0.0);
        leaveSummary1.setLeaveOpen(0.0);
        leaveSummary1.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary = Optional.of(leaveSummary1);
        when(mockLeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(any(LeaveType.class), eq(0L)))
                .thenReturn(leaveSummary);

        final LeaveSummary leaveSummary3 = new LeaveSummary();
        leaveSummary3.setId(0L);
        leaveSummary3.setLeavesTaken(0.0);
        leaveSummary3.setLeavesAvailable(0.0);
        leaveSummary3.setLeaveOpen(0.0);
        leaveSummary3.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary2 = Optional.of(leaveSummary3);
        doThrow(CommonException.class).when(mockBusinessValidationService).validateAvailableLeave(leaveSummary2, 0.0);

        assertThrows(CommonException.class,
                () -> leaveTransactionServiceImplUnderTest.saveLeave(applyLeaveRequest, "createdByEmpId"));
        verify(mockBusinessValidationService).duplicateLeaveCheck(any(EmployeeDetail.class),
                any(LeaveApplyRequest.class));
        verify(mockBusinessValidationService).validateCompOffLeave(any(LeaveType.class), any(LeaveApplyRequest.class));
    }

    @Test
    public void testSaveLeave_CompOffTransactionRepoFindAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAscReturnsNoItems() throws Exception {
        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("employeeId");
        applyLeaveRequest.setLeaveTypeId("leaveTypeId");
        applyLeaveRequest.setStartDt("startDt");
        applyLeaveRequest.setEndDt("endDt");
        applyLeaveRequest.setNoOfDays("noOfDays");

        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("empId");
        employeeDetail.setFirstName("firstName");
        employeeDetail.setLastName("lastName");
        employeeDetail.setOfficialEmail("officialEmail");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        final LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(0L);
        leaveType1.setName("name");
        leaveType1.setCode("code");
        leaveType1.setDescription("description");
        leaveType1.setLeavesAllowed(0.0);
        final Optional<LeaveType> leaveType = Optional.of(leaveType1);
        when(mockLeaveTypeRepo.findByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(leaveType);

        final LeaveTransaction leaveTransaction = new LeaveTransaction();
        leaveTransaction.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction.setLeaveTypeId(leaveTypeId);
        leaveTransaction.setNoOfDays(0.0);
        leaveTransaction.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction.setReportingManager(0L);
        leaveTransaction.setApproverRemarks("comment");
        leaveTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setLastUpdatedBy("firstName");
        when(mockLeaveMapper.leaveModelToLeaveEntity(any(LeaveApplyRequest.class), any(EmployeeDetail.class),
                any(LeaveType.class))).thenReturn(leaveTransaction);

        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId1 = new LeaveType();
        leaveTypeId1.setId(0L);
        leaveTypeId1.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId1);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        when(mockLeaveTransactionRepo.save(any(LeaveTransaction.class))).thenReturn(leaveTransaction1);

        // Configure LeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(...).
        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(0L);
        leaveSummary1.setLeavesTaken(0.0);
        leaveSummary1.setLeavesAvailable(0.0);
        leaveSummary1.setLeaveOpen(0.0);
        leaveSummary1.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary = Optional.of(leaveSummary1);
        when(mockLeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(any(LeaveType.class), eq(0L)))
                .thenReturn(leaveSummary);

        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(
                0L, Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)), EnumCompOffTransactionStatus.APPROVED,
                List.of(EnumCompOffUsageStatus.AVAILABLE), EnumStatus.ACTIVE)).thenReturn(Collections.emptyList());

        // Configure LeaveMapper.leaveEntityToModel(...).
        final LeaveResponse leaveResponse = new LeaveResponse();
        leaveResponse.setEmployeeId("employeeId");
        leaveResponse.setLeaveTypeId("leaveTypeId");
        leaveResponse.setStartDt("startDt");
        leaveResponse.setEndDt("endDt");
        leaveResponse.setNoOfDays("noOfDays");
        when(mockLeaveMapper.leaveEntityToModel(any(LeaveTransaction.class))).thenReturn(leaveResponse);

        // Run the test
        final LeaveResponse result = leaveTransactionServiceImplUnderTest.saveLeave(applyLeaveRequest,
                "createdByEmpId");

        // Verify the results
        verify(mockBusinessValidationService).duplicateLeaveCheck(any(EmployeeDetail.class),
                any(LeaveApplyRequest.class));
        verify(mockBusinessValidationService).validateCompOffLeave(any(LeaveType.class), any(LeaveApplyRequest.class));

        // Confirm BusinessValidationService.validateAvailableLeave(...).
        final LeaveSummary leaveSummary3 = new LeaveSummary();
        leaveSummary3.setId(0L);
        leaveSummary3.setLeavesTaken(0.0);
        leaveSummary3.setLeavesAvailable(0.0);
        leaveSummary3.setLeaveOpen(0.0);
        leaveSummary3.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary2 = Optional.of(leaveSummary3);
        verify(mockBusinessValidationService).validateAvailableLeave(leaveSummary2, 0.0);
    }

    @Test
    public void testSaveLeave_CompOffTransactionRepoFindAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAscReturnsNoItems() throws Exception {
        // Setup
        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("employeeId");
        applyLeaveRequest.setLeaveTypeId("leaveTypeId");
        applyLeaveRequest.setStartDt("startDt");
        applyLeaveRequest.setEndDt("endDt");
        applyLeaveRequest.setNoOfDays("noOfDays");

        // Configure BusinessValidationService.findEmployeeDetail(...).
        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("empId");
        employeeDetail.setFirstName("firstName");
        employeeDetail.setLastName("lastName");
        employeeDetail.setOfficialEmail("officialEmail");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        // Configure LeaveTypeRepo.findByIdAndStatus(...).
        final LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(0L);
        leaveType1.setName("name");
        leaveType1.setCode("code");
        leaveType1.setDescription("description");
        leaveType1.setLeavesAllowed(0.0);
        final Optional<LeaveType> leaveType = Optional.of(leaveType1);
        when(mockLeaveTypeRepo.findByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(leaveType);

        // Configure LeaveMapper.leaveModelToLeaveEntity(...).
        final LeaveTransaction leaveTransaction = new LeaveTransaction();
        leaveTransaction.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction.setLeaveTypeId(leaveTypeId);
        leaveTransaction.setNoOfDays(0.0);
        leaveTransaction.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction.setReportingManager(0L);
        leaveTransaction.setApproverRemarks("comment");
        leaveTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction.setLastUpdatedBy("firstName");
        when(mockLeaveMapper.leaveModelToLeaveEntity(any(LeaveApplyRequest.class), any(EmployeeDetail.class),
                any(LeaveType.class))).thenReturn(leaveTransaction);

        // Configure LeaveTransactionRepo.save(...).
        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId1 = new LeaveType();
        leaveTypeId1.setId(0L);
        leaveTypeId1.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId1);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        when(mockLeaveTransactionRepo.save(any(LeaveTransaction.class))).thenReturn(leaveTransaction1);

        // Configure LeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(...).
        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(0L);
        leaveSummary1.setLeavesTaken(0.0);
        leaveSummary1.setLeavesAvailable(0.0);
        leaveSummary1.setLeaveOpen(0.0);
        leaveSummary1.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary = Optional.of(leaveSummary1);
        when(mockLeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(any(LeaveType.class), eq(0L)))
                .thenReturn(leaveSummary);

        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc(
                eq(0L), eq(EnumCompOffTransactionStatus.APPROVED), eq(List.of(EnumCompOffUsageStatus.AVAILABLE)),
                eq(EnumStatus.ACTIVE), any(LeaveTransaction.class))).thenReturn(Collections.emptyList());

        // Configure LeaveMapper.leaveEntityToModel(...).
        final LeaveResponse leaveResponse = new LeaveResponse();
        leaveResponse.setEmployeeId("employeeId");
        leaveResponse.setLeaveTypeId("leaveTypeId");
        leaveResponse.setStartDt("startDt");
        leaveResponse.setEndDt("endDt");
        leaveResponse.setNoOfDays("noOfDays");
        when(mockLeaveMapper.leaveEntityToModel(any(LeaveTransaction.class))).thenReturn(leaveResponse);

        // Run the test
        final LeaveResponse result = leaveTransactionServiceImplUnderTest.saveLeave(applyLeaveRequest,
                "createdByEmpId");

        // Verify the results
        verify(mockBusinessValidationService).duplicateLeaveCheck(any(EmployeeDetail.class),
                any(LeaveApplyRequest.class));
        verify(mockBusinessValidationService).validateCompOffLeave(any(LeaveType.class), any(LeaveApplyRequest.class));

        // Confirm BusinessValidationService.validateAvailableLeave(...).
        final LeaveSummary leaveSummary3 = new LeaveSummary();
        leaveSummary3.setId(0L);
        leaveSummary3.setLeavesTaken(0.0);
        leaveSummary3.setLeavesAvailable(0.0);
        leaveSummary3.setLeaveOpen(0.0);
        leaveSummary3.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary2 = Optional.of(leaveSummary3);
        verify(mockBusinessValidationService).validateAvailableLeave(leaveSummary2, 0.0);
    }

    @Test
    public void testUpdateLeaveTransaction() throws Exception {
        // Setup
        // Configure LeaveTransactionRepo.findById(...).
        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        final Optional<LeaveTransaction> leaveTransaction = Optional.of(leaveTransaction1);
        when(mockLeaveTransactionRepo.findById(0L)).thenReturn(leaveTransaction);

        // Configure LeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(...).
        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(0L);
        leaveSummary1.setLeavesTaken(0.0);
        leaveSummary1.setLeavesAvailable(0.0);
        leaveSummary1.setLeaveOpen(0.0);
        leaveSummary1.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary = Optional.of(leaveSummary1);
        when(mockLeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(any(LeaveType.class), eq(0L)))
                .thenReturn(leaveSummary);

        // Configure LeaveMapper.mapLeaveSummaryEntity(...).
        final LeaveSummary leaveSummary2 = new LeaveSummary();
        leaveSummary2.setId(0L);
        leaveSummary2.setLeavesTaken(0.0);
        leaveSummary2.setLeavesAvailable(0.0);
        leaveSummary2.setLeaveOpen(0.0);
        leaveSummary2.setLeaveCredit(0.0);
        when(mockLeaveMapper.mapLeaveSummaryEntity(any(LeaveTransaction.class), eq(0.0), eq(0.0), eq(0L), eq(0.0),
                eq(0.0))).thenReturn(leaveSummary2);

        // Configure LeaveSummaryRepo.save(...).
        final LeaveSummary leaveSummary3 = new LeaveSummary();
        leaveSummary3.setId(0L);
        leaveSummary3.setLeavesTaken(0.0);
        leaveSummary3.setLeavesAvailable(0.0);
        leaveSummary3.setLeaveOpen(0.0);
        leaveSummary3.setLeaveCredit(0.0);
        when(mockLeaveSummaryRepo.save(any(LeaveSummary.class))).thenReturn(leaveSummary3);

        // Configure CompOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(...).
        final CompOffTransaction compOffTransaction = new CompOffTransaction();
        compOffTransaction.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction2 = new LeaveTransaction();
        leaveTransaction2.setEmployeeId(0L);
        final LeaveType leaveTypeId1 = new LeaveType();
        leaveTypeId1.setId(0L);
        leaveTypeId1.setLeavesAllowed(0.0);
        leaveTransaction2.setLeaveTypeId(leaveTypeId1);
        leaveTransaction2.setNoOfDays(0.0);
        leaveTransaction2.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction2.setReportingManager(0L);
        leaveTransaction2.setApproverRemarks("comment");
        leaveTransaction2.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction2.setLastUpdatedBy("firstName");
        compOffTransaction.setLeaveTransaction(leaveTransaction2);
        compOffTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> compOffTransactions = List.of(compOffTransaction);
        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(
                0L, Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)), EnumCompOffTransactionStatus.APPROVED,
                List.of(EnumCompOffUsageStatus.AVAILABLE), EnumStatus.ACTIVE)).thenReturn(compOffTransactions);

        // Configure CompOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc(...).
        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction1.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction3 = new LeaveTransaction();
        leaveTransaction3.setEmployeeId(0L);
        final LeaveType leaveTypeId2 = new LeaveType();
        leaveTypeId2.setId(0L);
        leaveTypeId2.setLeavesAllowed(0.0);
        leaveTransaction3.setLeaveTypeId(leaveTypeId2);
        leaveTransaction3.setNoOfDays(0.0);
        leaveTransaction3.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction3.setReportingManager(0L);
        leaveTransaction3.setApproverRemarks("comment");
        leaveTransaction3.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction3.setLastUpdatedBy("firstName");
        compOffTransaction1.setLeaveTransaction(leaveTransaction3);
        compOffTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> compOffTransactions1 = List.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc(
                eq(0L), eq(EnumCompOffTransactionStatus.APPROVED), eq(List.of(EnumCompOffUsageStatus.AVAILABLE)),
                eq(EnumStatus.ACTIVE), any(LeaveTransaction.class))).thenReturn(compOffTransactions1);

        // Run the test
        leaveTransactionServiceImplUnderTest.updateLeaveTransaction("transactionId", "status", "employeeId", "comment");

        // Verify the results
        verify(mockBusinessValidationService).findEmployeeDetail(0L);
        verify(mockLeaveTransactionRepo).save(any(LeaveTransaction.class));

        // Confirm CompOffTransactionRepo.saveAll(...).
        final CompOffTransaction compOffTransaction2 = new CompOffTransaction();
        compOffTransaction2.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction2.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction4 = new LeaveTransaction();
        leaveTransaction4.setEmployeeId(0L);
        final LeaveType leaveTypeId3 = new LeaveType();
        leaveTypeId3.setId(0L);
        leaveTypeId3.setLeavesAllowed(0.0);
        leaveTransaction4.setLeaveTypeId(leaveTypeId3);
        leaveTransaction4.setNoOfDays(0.0);
        leaveTransaction4.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction4.setReportingManager(0L);
        leaveTransaction4.setApproverRemarks("comment");
        leaveTransaction4.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction4.setLastUpdatedBy("firstName");
        compOffTransaction2.setLeaveTransaction(leaveTransaction4);
        compOffTransaction2.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction2.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> entities = List.of(compOffTransaction2);
        verify(mockCompOffTransactionRepo).saveAll(entities);
    }

    @Test
    public void testUpdateLeaveTransaction_LeaveTransactionRepoFindByIdReturnsAbsent() {
        // Setup
        when(mockLeaveTransactionRepo.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThrows(DataNotFoundException.class,
                () -> leaveTransactionServiceImplUnderTest.updateLeaveTransaction("transactionId", "status",
                        "employeeId", "comment"));
    }

    @Test
    public void testUpdateLeaveTransaction_BusinessValidationServiceThrowsDataNotFoundException() throws Exception {
        // Setup
        // Configure LeaveTransactionRepo.findById(...).
        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        final Optional<LeaveTransaction> leaveTransaction = Optional.of(leaveTransaction1);
        when(mockLeaveTransactionRepo.findById(0L)).thenReturn(leaveTransaction);

        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenThrow(DataNotFoundException.class);

        // Run the test
        assertThrows(DataNotFoundException.class,
                () -> leaveTransactionServiceImplUnderTest.updateLeaveTransaction("transactionId", "status",
                        "employeeId", "comment"));
    }

    @Test
    public void testUpdateLeaveTransaction_LeaveSummaryRepoFindByLeaveTypeIdAndEmployeeIdReturnsAbsent() throws Exception {
        // Setup
        // Configure LeaveTransactionRepo.findById(...).
        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        final Optional<LeaveTransaction> leaveTransaction = Optional.of(leaveTransaction1);
        when(mockLeaveTransactionRepo.findById(0L)).thenReturn(leaveTransaction);

        when(mockLeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(any(LeaveType.class), eq(0L)))
                .thenReturn(Optional.empty());

        // Configure LeaveTypeRepo.findById(...).
        final LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(0L);
        leaveType1.setName("name");
        leaveType1.setCode("code");
        leaveType1.setDescription("description");
        leaveType1.setLeavesAllowed(0.0);
        final Optional<LeaveType> leaveType = Optional.of(leaveType1);
        when(mockLeaveTypeRepo.findById(0L)).thenReturn(leaveType);

        // Configure LeaveMapper.mapLeaveSummaryEntity(...).
        final LeaveSummary leaveSummary = new LeaveSummary();
        leaveSummary.setId(0L);
        leaveSummary.setLeavesTaken(0.0);
        leaveSummary.setLeavesAvailable(0.0);
        leaveSummary.setLeaveOpen(0.0);
        leaveSummary.setLeaveCredit(0.0);
        when(mockLeaveMapper.mapLeaveSummaryEntity(any(LeaveTransaction.class), eq(0.0), eq(0.0), eq(0L), eq(0.0),
                eq(0.0))).thenReturn(leaveSummary);

        // Configure LeaveSummaryRepo.save(...).
        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(0L);
        leaveSummary1.setLeavesTaken(0.0);
        leaveSummary1.setLeavesAvailable(0.0);
        leaveSummary1.setLeaveOpen(0.0);
        leaveSummary1.setLeaveCredit(0.0);
        when(mockLeaveSummaryRepo.save(any(LeaveSummary.class))).thenReturn(leaveSummary1);

        // Configure CompOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(...).
        final CompOffTransaction compOffTransaction = new CompOffTransaction();
        compOffTransaction.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction2 = new LeaveTransaction();
        leaveTransaction2.setEmployeeId(0L);
        final LeaveType leaveTypeId1 = new LeaveType();
        leaveTypeId1.setId(0L);
        leaveTypeId1.setLeavesAllowed(0.0);
        leaveTransaction2.setLeaveTypeId(leaveTypeId1);
        leaveTransaction2.setNoOfDays(0.0);
        leaveTransaction2.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction2.setReportingManager(0L);
        leaveTransaction2.setApproverRemarks("comment");
        leaveTransaction2.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction2.setLastUpdatedBy("firstName");
        compOffTransaction.setLeaveTransaction(leaveTransaction2);
        compOffTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> compOffTransactions = List.of(compOffTransaction);
        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(
                0L, Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)), EnumCompOffTransactionStatus.APPROVED,
                List.of(EnumCompOffUsageStatus.AVAILABLE), EnumStatus.ACTIVE)).thenReturn(compOffTransactions);

        // Configure CompOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc(...).
        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction1.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction3 = new LeaveTransaction();
        leaveTransaction3.setEmployeeId(0L);
        final LeaveType leaveTypeId2 = new LeaveType();
        leaveTypeId2.setId(0L);
        leaveTypeId2.setLeavesAllowed(0.0);
        leaveTransaction3.setLeaveTypeId(leaveTypeId2);
        leaveTransaction3.setNoOfDays(0.0);
        leaveTransaction3.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction3.setReportingManager(0L);
        leaveTransaction3.setApproverRemarks("comment");
        leaveTransaction3.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction3.setLastUpdatedBy("firstName");
        compOffTransaction1.setLeaveTransaction(leaveTransaction3);
        compOffTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> compOffTransactions1 = List.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc(
                eq(0L), eq(EnumCompOffTransactionStatus.APPROVED), eq(List.of(EnumCompOffUsageStatus.AVAILABLE)),
                eq(EnumStatus.ACTIVE), any(LeaveTransaction.class))).thenReturn(compOffTransactions1);

        // Run the test
        leaveTransactionServiceImplUnderTest.updateLeaveTransaction("transactionId", "status", "employeeId", "comment");

        // Verify the results
        verify(mockBusinessValidationService).findEmployeeDetail(0L);
        verify(mockLeaveTransactionRepo).save(any(LeaveTransaction.class));

        // Confirm CompOffTransactionRepo.saveAll(...).
        final CompOffTransaction compOffTransaction2 = new CompOffTransaction();
        compOffTransaction2.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction2.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction4 = new LeaveTransaction();
        leaveTransaction4.setEmployeeId(0L);
        final LeaveType leaveTypeId3 = new LeaveType();
        leaveTypeId3.setId(0L);
        leaveTypeId3.setLeavesAllowed(0.0);
        leaveTransaction4.setLeaveTypeId(leaveTypeId3);
        leaveTransaction4.setNoOfDays(0.0);
        leaveTransaction4.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction4.setReportingManager(0L);
        leaveTransaction4.setApproverRemarks("comment");
        leaveTransaction4.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction4.setLastUpdatedBy("firstName");
        compOffTransaction2.setLeaveTransaction(leaveTransaction4);
        compOffTransaction2.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction2.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> entities = List.of(compOffTransaction2);
        verify(mockCompOffTransactionRepo).saveAll(entities);
    }

    @Test
    public void testUpdateLeaveTransaction_LeaveTypeRepoReturnsAbsent() throws Exception {
        // Setup
        // Configure LeaveTransactionRepo.findById(...).
        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        final Optional<LeaveTransaction> leaveTransaction = Optional.of(leaveTransaction1);
        when(mockLeaveTransactionRepo.findById(0L)).thenReturn(leaveTransaction);

        when(mockLeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(any(LeaveType.class), eq(0L)))
                .thenReturn(Optional.empty());
        when(mockLeaveTypeRepo.findById(0L)).thenReturn(Optional.empty());

        // Configure LeaveMapper.mapLeaveSummaryEntity(...).
        final LeaveSummary leaveSummary = new LeaveSummary();
        leaveSummary.setId(0L);
        leaveSummary.setLeavesTaken(0.0);
        leaveSummary.setLeavesAvailable(0.0);
        leaveSummary.setLeaveOpen(0.0);
        leaveSummary.setLeaveCredit(0.0);
        when(mockLeaveMapper.mapLeaveSummaryEntity(any(LeaveTransaction.class), eq(0.0), eq(0.0), eq(0L), eq(0.0),
                eq(0.0))).thenReturn(leaveSummary);

        // Configure LeaveSummaryRepo.save(...).
        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(0L);
        leaveSummary1.setLeavesTaken(0.0);
        leaveSummary1.setLeavesAvailable(0.0);
        leaveSummary1.setLeaveOpen(0.0);
        leaveSummary1.setLeaveCredit(0.0);
        when(mockLeaveSummaryRepo.save(any(LeaveSummary.class))).thenReturn(leaveSummary1);

        // Configure CompOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(...).
        final CompOffTransaction compOffTransaction = new CompOffTransaction();
        compOffTransaction.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction2 = new LeaveTransaction();
        leaveTransaction2.setEmployeeId(0L);
        final LeaveType leaveTypeId1 = new LeaveType();
        leaveTypeId1.setId(0L);
        leaveTypeId1.setLeavesAllowed(0.0);
        leaveTransaction2.setLeaveTypeId(leaveTypeId1);
        leaveTransaction2.setNoOfDays(0.0);
        leaveTransaction2.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction2.setReportingManager(0L);
        leaveTransaction2.setApproverRemarks("comment");
        leaveTransaction2.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction2.setLastUpdatedBy("firstName");
        compOffTransaction.setLeaveTransaction(leaveTransaction2);
        compOffTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> compOffTransactions = List.of(compOffTransaction);
        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(
                0L, Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)), EnumCompOffTransactionStatus.APPROVED,
                List.of(EnumCompOffUsageStatus.AVAILABLE), EnumStatus.ACTIVE)).thenReturn(compOffTransactions);

        // Configure CompOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc(...).
        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction1.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction3 = new LeaveTransaction();
        leaveTransaction3.setEmployeeId(0L);
        final LeaveType leaveTypeId2 = new LeaveType();
        leaveTypeId2.setId(0L);
        leaveTypeId2.setLeavesAllowed(0.0);
        leaveTransaction3.setLeaveTypeId(leaveTypeId2);
        leaveTransaction3.setNoOfDays(0.0);
        leaveTransaction3.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction3.setReportingManager(0L);
        leaveTransaction3.setApproverRemarks("comment");
        leaveTransaction3.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction3.setLastUpdatedBy("firstName");
        compOffTransaction1.setLeaveTransaction(leaveTransaction3);
        compOffTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> compOffTransactions1 = List.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc(
                eq(0L), eq(EnumCompOffTransactionStatus.APPROVED), eq(List.of(EnumCompOffUsageStatus.AVAILABLE)),
                eq(EnumStatus.ACTIVE), any(LeaveTransaction.class))).thenReturn(compOffTransactions1);

        // Run the test
        leaveTransactionServiceImplUnderTest.updateLeaveTransaction("transactionId", "status", "employeeId", "comment");

        // Verify the results
        verify(mockBusinessValidationService).findEmployeeDetail(0L);
        verify(mockLeaveTransactionRepo).save(any(LeaveTransaction.class));

        // Confirm CompOffTransactionRepo.saveAll(...).
        final CompOffTransaction compOffTransaction2 = new CompOffTransaction();
        compOffTransaction2.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction2.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        final LeaveTransaction leaveTransaction4 = new LeaveTransaction();
        leaveTransaction4.setEmployeeId(0L);
        final LeaveType leaveTypeId3 = new LeaveType();
        leaveTypeId3.setId(0L);
        leaveTypeId3.setLeavesAllowed(0.0);
        leaveTransaction4.setLeaveTypeId(leaveTypeId3);
        leaveTransaction4.setNoOfDays(0.0);
        leaveTransaction4.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction4.setReportingManager(0L);
        leaveTransaction4.setApproverRemarks("comment");
        leaveTransaction4.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction4.setLastUpdatedBy("firstName");
        compOffTransaction2.setLeaveTransaction(leaveTransaction4);
        compOffTransaction2.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction2.setLastUpdatedBy("firstName");
        final List<CompOffTransaction> entities = List.of(compOffTransaction2);
        verify(mockCompOffTransactionRepo).saveAll(entities);
    }

    @Test
    public void testUpdateLeaveTransaction_CompOffTransactionRepoFindAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAscReturnsNoItems() throws Exception {
        // Setup
        // Configure LeaveTransactionRepo.findById(...).
        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        final Optional<LeaveTransaction> leaveTransaction = Optional.of(leaveTransaction1);
        when(mockLeaveTransactionRepo.findById(0L)).thenReturn(leaveTransaction);

        // Configure LeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(...).
        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(0L);
        leaveSummary1.setLeavesTaken(0.0);
        leaveSummary1.setLeavesAvailable(0.0);
        leaveSummary1.setLeaveOpen(0.0);
        leaveSummary1.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary = Optional.of(leaveSummary1);
        when(mockLeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(any(LeaveType.class), eq(0L)))
                .thenReturn(leaveSummary);

        // Configure LeaveMapper.mapLeaveSummaryEntity(...).
        final LeaveSummary leaveSummary2 = new LeaveSummary();
        leaveSummary2.setId(0L);
        leaveSummary2.setLeavesTaken(0.0);
        leaveSummary2.setLeavesAvailable(0.0);
        leaveSummary2.setLeaveOpen(0.0);
        leaveSummary2.setLeaveCredit(0.0);
        when(mockLeaveMapper.mapLeaveSummaryEntity(any(LeaveTransaction.class), eq(0.0), eq(0.0), eq(0L), eq(0.0),
                eq(0.0))).thenReturn(leaveSummary2);

        // Configure LeaveSummaryRepo.save(...).
        final LeaveSummary leaveSummary3 = new LeaveSummary();
        leaveSummary3.setId(0L);
        leaveSummary3.setLeavesTaken(0.0);
        leaveSummary3.setLeavesAvailable(0.0);
        leaveSummary3.setLeaveOpen(0.0);
        leaveSummary3.setLeaveCredit(0.0);
        when(mockLeaveSummaryRepo.save(any(LeaveSummary.class))).thenReturn(leaveSummary3);

        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(
                0L, Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)), EnumCompOffTransactionStatus.APPROVED,
                List.of(EnumCompOffUsageStatus.AVAILABLE), EnumStatus.ACTIVE)).thenReturn(Collections.emptyList());

        // Run the test
        leaveTransactionServiceImplUnderTest.updateLeaveTransaction("transactionId", "status", "employeeId", "comment");

        // Verify the results
        verify(mockBusinessValidationService).findEmployeeDetail(0L);
        verify(mockLeaveTransactionRepo).save(any(LeaveTransaction.class));
    }

    @Test
    public void testUpdateLeaveTransaction_CompOffTransactionRepoFindAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAscReturnsNoItems() throws Exception {
        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setLeavesAllowed(0.0);
        leaveTransaction1.setLeaveTypeId(leaveTypeId);
        leaveTransaction1.setNoOfDays(0.0);
        leaveTransaction1.setLeaveStatus(EnumLeaveStatus.PENDING);
        leaveTransaction1.setReportingManager(0L);
        leaveTransaction1.setApproverRemarks("comment");
        leaveTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveTransaction1.setLastUpdatedBy("firstName");
        final Optional<LeaveTransaction> leaveTransaction = Optional.of(leaveTransaction1);
        when(mockLeaveTransactionRepo.findById(0L)).thenReturn(leaveTransaction);

        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(0L);
        leaveSummary1.setLeavesTaken(0.0);
        leaveSummary1.setLeavesAvailable(0.0);
        leaveSummary1.setLeaveOpen(0.0);
        leaveSummary1.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary = Optional.of(leaveSummary1);
        when(mockLeaveSummaryRepo.findByLeaveTypeIdAndEmployeeId(any(LeaveType.class), eq(0L)))
                .thenReturn(leaveSummary);

        final LeaveSummary leaveSummary2 = new LeaveSummary();
        leaveSummary2.setId(0L);
        leaveSummary2.setLeavesTaken(0.0);
        leaveSummary2.setLeavesAvailable(0.0);
        leaveSummary2.setLeaveOpen(0.0);
        leaveSummary2.setLeaveCredit(0.0);
        when(mockLeaveMapper.mapLeaveSummaryEntity(any(LeaveTransaction.class), eq(0.0), eq(0.0), eq(0L), eq(0.0),
                eq(0.0))).thenReturn(leaveSummary2);

        final LeaveSummary leaveSummary3 = new LeaveSummary();
        leaveSummary3.setId(0L);
        leaveSummary3.setLeavesTaken(0.0);
        leaveSummary3.setLeavesAvailable(0.0);
        leaveSummary3.setLeaveOpen(0.0);
        leaveSummary3.setLeaveCredit(0.0);
        when(mockLeaveSummaryRepo.save(any(LeaveSummary.class))).thenReturn(leaveSummary3);

        when(mockCompOffTransactionRepo.findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc(
                eq(0L), eq(EnumCompOffTransactionStatus.APPROVED), eq(List.of(EnumCompOffUsageStatus.AVAILABLE)),
                eq(EnumStatus.ACTIVE), any(LeaveTransaction.class))).thenReturn(Collections.emptyList());

        leaveTransactionServiceImplUnderTest.updateLeaveTransaction("20", "status", "employeeId", "comment");
        verify(mockBusinessValidationService).findEmployeeDetail(0L);
        verify(mockLeaveTransactionRepo).save(any(LeaveTransaction.class));
    }
}
