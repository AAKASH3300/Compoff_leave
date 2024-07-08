package com.itime.compoff.service.impl;

import com.itime.compoff.enumeration.EnumCompOffPeriod;
import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumCompOffUsageStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
import com.itime.compoff.mapper.CompOffMapper;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.entity.LeaveSummary;
import com.itime.compoff.primary.entity.LeaveType;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
import com.itime.compoff.primary.repository.LeaveSummaryRepo;
import com.itime.compoff.primary.repository.LeaveTypeRepo;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.validation.BusinessValidationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CompOffTransactionServiceImplTest {

    @Mock
    private CompOffTransactionRepo mockCompOffTransactionRepo;
    @Mock
    private LeaveSummaryRepo mockLeaveSummaryRepo;
    @Mock
    private CompOffMapper mockCompOffMapper;
    @Mock
    private LeaveTypeRepo mockLeaveTypeRepo;
    @Mock
    private BusinessValidationService mockBusinessValidationService;

    private CompOffTransactionServiceImpl compOffTransactionServiceImplUnderTest;

    @Before
    public void setUp() {
        compOffTransactionServiceImplUnderTest = new CompOffTransactionServiceImpl();
        compOffTransactionServiceImplUnderTest.compOffTransactionRepo = mockCompOffTransactionRepo;
        compOffTransactionServiceImplUnderTest.leaveSummaryRepo = mockLeaveSummaryRepo;
        compOffTransactionServiceImplUnderTest.compOffMapper = mockCompOffMapper;
        compOffTransactionServiceImplUnderTest.leaveTypeRepo = mockLeaveTypeRepo;
        compOffTransactionServiceImplUnderTest.businessValidationService = mockBusinessValidationService;
    }

    @Test
    public void testCreateCompOffTransaction() throws Exception {

        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setEmployeeId(1L);
        compOffApplyRequest.setRequestedDate("2024-07-08 00:00:00");
        compOffApplyRequest.setPunchIn("11:00:00");
        compOffApplyRequest.setPunchOut("19:00:00");
        compOffApplyRequest.setWorkHours("8");

        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("101");
        employeeDetail.setFirstName("Aakash");
        employeeDetail.setLastName("B");
        employeeDetail.setOfficialEmail("aakash.b@aaludra.com");
        when(mockBusinessValidationService.findEmployeeDetail(1L)).thenReturn(employeeDetail);

        final CompOffTransaction compOffTransaction = new CompOffTransaction();
        compOffTransaction.setEmployeeId(1);
        compOffTransaction.setPunchInTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction.setPunchOutTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction.setApproverRemarks("comment");
        compOffTransaction.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction.setTransactionStatus(EnumCompOffTransactionStatus.PENDING);
        compOffTransaction.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        compOffTransaction.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction.setLastUpdatedBy("firstName");
        when(mockCompOffMapper.mapApplyRequestToEntity(any(EmployeeDetail.class),
                any(CompOffApplyRequest.class))).thenReturn(compOffTransaction);

        // Run the test
        final HttpStatus result = compOffTransactionServiceImplUnderTest.createCompOffTransaction(compOffApplyRequest);

        // Verify the results
        assertEquals(HttpStatus.OK, result);
        verify(mockBusinessValidationService).duplicateCompOffValidation(any(EmployeeDetail.class),
                any(CompOffApplyRequest.class));
        verify(mockBusinessValidationService).validateCompOff(any(CompOffApplyRequest.class),
                eq(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0))));
        verify(mockCompOffTransactionRepo).save(any(CompOffTransaction.class));
    }

    @Test
    public void testCreateCompOffTransaction_BusinessValidationServiceFindEmployeeDetailThrowsDataNotFoundException() throws Exception {
        // Setup
        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setEmployeeId(0L);
        compOffApplyRequest.setRequestedDate("requestedDate");
        compOffApplyRequest.setPunchIn("punchIn");
        compOffApplyRequest.setPunchOut("punchOut");
        compOffApplyRequest.setWorkHours("workHours");

        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenThrow(DataNotFoundException.class);

        // Run the test
        assertThrows(DataNotFoundException.class,
                () -> compOffTransactionServiceImplUnderTest.createCompOffTransaction(compOffApplyRequest));
    }

    @Test
    public void testCreateCompOffTransaction_BusinessValidationServiceDuplicateCompOffValidationThrowsCommonException() throws Exception {
        // Setup
        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setEmployeeId(0L);
        compOffApplyRequest.setRequestedDate("requestedDate");
        compOffApplyRequest.setPunchIn("punchIn");
        compOffApplyRequest.setPunchOut("punchOut");
        compOffApplyRequest.setWorkHours("workHours");

        // Configure BusinessValidationService.findEmployeeDetail(...).
        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("empId");
        employeeDetail.setFirstName("firstName");
        employeeDetail.setLastName("lastName");
        employeeDetail.setOfficialEmail("officialEmail");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        doThrow(CommonException.class).when(mockBusinessValidationService).duplicateCompOffValidation(
                any(EmployeeDetail.class), any(CompOffApplyRequest.class));

        // Run the test
        assertThrows(CommonException.class,
                () -> compOffTransactionServiceImplUnderTest.createCompOffTransaction(compOffApplyRequest));
    }

    @Test
    public void testCreateCompOffTransaction_BusinessValidationServiceValidateCompOffThrowsCommonException() throws Exception {
        // Setup
        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setEmployeeId(0L);
        compOffApplyRequest.setRequestedDate("requestedDate");
        compOffApplyRequest.setPunchIn("punchIn");
        compOffApplyRequest.setPunchOut("punchOut");
        compOffApplyRequest.setWorkHours("workHours");

        // Configure BusinessValidationService.findEmployeeDetail(...).
        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("empId");
        employeeDetail.setFirstName("firstName");
        employeeDetail.setLastName("lastName");
        employeeDetail.setOfficialEmail("officialEmail");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        doThrow(CommonException.class).when(mockBusinessValidationService).validateCompOff(
                any(CompOffApplyRequest.class), eq(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0))));

        // Run the test
        assertThrows(CommonException.class,
                () -> compOffTransactionServiceImplUnderTest.createCompOffTransaction(compOffApplyRequest));
        verify(mockBusinessValidationService).duplicateCompOffValidation(any(EmployeeDetail.class),
                any(CompOffApplyRequest.class));
    }

    @Test
    public void testUpdateCompOffApproval() throws Exception {
        // Setup
        // Configure CompOffTransactionRepo.findTop1ByIdAndStatus(...).
        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setEmployeeId(0L);
        compOffTransaction1.setPunchInTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction1.setPunchOutTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction1.setApproverRemarks("comment");
        compOffTransaction1.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction1.setTransactionStatus(EnumCompOffTransactionStatus.PENDING);
        compOffTransaction1.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        compOffTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setLastUpdatedBy("firstName");
        final Optional<CompOffTransaction> compOffTransaction = Optional.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findTop1ByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(compOffTransaction);

        // Configure BusinessValidationService.findEmployeeDetail(...).
        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("empId");
        employeeDetail.setFirstName("firstName");
        employeeDetail.setLastName("lastName");
        employeeDetail.setOfficialEmail("officialEmail");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        when(mockBusinessValidationService.findTimeDifference(Time.valueOf(LocalTime.of(0, 0, 0)),
                Time.valueOf(LocalTime.of(0, 0, 0)))).thenReturn(0L);

        // Configure LeaveTypeRepo.findByIdAndStatus(...).
        final LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(0L);
        leaveType1.setName("name");
        leaveType1.setCode("code");
        leaveType1.setDescription("description");
        leaveType1.setLeavesAllowed(0.0);
        final Optional<LeaveType> leaveType = Optional.of(leaveType1);
        when(mockLeaveTypeRepo.findByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(leaveType);

        // Configure LeaveSummaryRepo.findTop1ByEmployeeIdAndLeaveTypeIdAndPeriodStartDtLessThanEqualAndPeriodEndDtGreaterThanEqual(...).
        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveSummary1.setLeaveTypeId(leaveTypeId);
        leaveSummary1.setPeriodStartDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveSummary1.setPeriodEndDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        leaveSummary1.setLeavesTaken(0.0);
        leaveSummary1.setLeavesAvailable(0.0);
        leaveSummary1.setLeaveOpen(0.0);
        leaveSummary1.setLeaveCredit(0.0);
        final Optional<LeaveSummary> leaveSummary = Optional.of(leaveSummary1);
        when(mockLeaveSummaryRepo.findTop1ByEmployeeIdAndLeaveTypeIdAndPeriodStartDtLessThanEqualAndPeriodEndDtGreaterThanEqual(
                eq(0L), any(LeaveType.class), eq(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0))),
                eq(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0))))).thenReturn(leaveSummary);

        // Run the test
        compOffTransactionServiceImplUnderTest.updateCompOffApproval("transactionId", "status", "compOffFor",
                "comment");

        // Verify the results
        verify(mockBusinessValidationService).validateWorkHours("punchIn", "punchOut", EnumCompOffPeriod.HALF_DAY, 0L);
        verify(mockCompOffTransactionRepo).save(any(CompOffTransaction.class));
        verify(mockLeaveSummaryRepo).save(any(LeaveSummary.class));
    }

    @Test
    public void testUpdateCompOffApproval_CompOffTransactionRepoFindTop1ByIdAndStatusReturnsAbsent() {
        // Setup
        when(mockCompOffTransactionRepo.findTop1ByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(Optional.empty());

        // Run the test
        assertThrows(DataNotFoundException.class,
                () -> compOffTransactionServiceImplUnderTest.updateCompOffApproval("transactionId", "status",
                        "compOffFor", "comment"));
    }

    @Test
    public void testUpdateCompOffApproval_BusinessValidationServiceFindEmployeeDetailThrowsDataNotFoundException() throws Exception {
        // Setup
        // Configure CompOffTransactionRepo.findTop1ByIdAndStatus(...).
        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setEmployeeId(0L);
        compOffTransaction1.setPunchInTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction1.setPunchOutTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction1.setApproverRemarks("comment");
        compOffTransaction1.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction1.setTransactionStatus(EnumCompOffTransactionStatus.PENDING);
        compOffTransaction1.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        compOffTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setLastUpdatedBy("firstName");
        final Optional<CompOffTransaction> compOffTransaction = Optional.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findTop1ByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(compOffTransaction);

        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenThrow(DataNotFoundException.class);

        // Run the test
        assertThrows(DataNotFoundException.class,
                () -> compOffTransactionServiceImplUnderTest.updateCompOffApproval("transactionId", "status",
                        "compOffFor", "comment"));
    }

    @Test
    public void testUpdateCompOffApproval_BusinessValidationServiceValidateWorkHoursThrowsCommonException() throws Exception {
        // Setup
        // Configure CompOffTransactionRepo.findTop1ByIdAndStatus(...).
        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setEmployeeId(0L);
        compOffTransaction1.setPunchInTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction1.setPunchOutTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction1.setApproverRemarks("comment");
        compOffTransaction1.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction1.setTransactionStatus(EnumCompOffTransactionStatus.PENDING);
        compOffTransaction1.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        compOffTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setLastUpdatedBy("firstName");
        final Optional<CompOffTransaction> compOffTransaction = Optional.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findTop1ByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(compOffTransaction);

        // Configure BusinessValidationService.findEmployeeDetail(...).
        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("empId");
        employeeDetail.setFirstName("firstName");
        employeeDetail.setLastName("lastName");
        employeeDetail.setOfficialEmail("officialEmail");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        when(mockBusinessValidationService.findTimeDifference(Time.valueOf(LocalTime.of(0, 0, 0)),
                Time.valueOf(LocalTime.of(0, 0, 0)))).thenReturn(0L);
        doThrow(CommonException.class).when(mockBusinessValidationService).validateWorkHours("punchIn", "punchOut",
                EnumCompOffPeriod.HALF_DAY, 0L);

        // Run the test
        assertThrows(CommonException.class,
                () -> compOffTransactionServiceImplUnderTest.updateCompOffApproval("transactionId", "status",
                        "compOffFor", "comment"));
    }

    @Test
    public void testUpdateCompOffApproval_LeaveTypeRepoReturnsAbsent() throws Exception {
        // Setup
        // Configure CompOffTransactionRepo.findTop1ByIdAndStatus(...).
        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setEmployeeId(0L);
        compOffTransaction1.setPunchInTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction1.setPunchOutTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction1.setApproverRemarks("comment");
        compOffTransaction1.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction1.setTransactionStatus(EnumCompOffTransactionStatus.PENDING);
        compOffTransaction1.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        compOffTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setLastUpdatedBy("firstName");
        final Optional<CompOffTransaction> compOffTransaction = Optional.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findTop1ByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(compOffTransaction);

        // Configure BusinessValidationService.findEmployeeDetail(...).
        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("empId");
        employeeDetail.setFirstName("firstName");
        employeeDetail.setLastName("lastName");
        employeeDetail.setOfficialEmail("officialEmail");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        when(mockBusinessValidationService.findTimeDifference(Time.valueOf(LocalTime.of(0, 0, 0)),
                Time.valueOf(LocalTime.of(0, 0, 0)))).thenReturn(0L);
        when(mockLeaveTypeRepo.findByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(Optional.empty());

        // Run the test
        assertThrows(DataNotFoundException.class,
                () -> compOffTransactionServiceImplUnderTest.updateCompOffApproval("transactionId", "status",
                        "compOffFor", "comment"));
        verify(mockBusinessValidationService).validateWorkHours("punchIn", "punchOut", EnumCompOffPeriod.HALF_DAY, 0L);
        verify(mockCompOffTransactionRepo).save(any(CompOffTransaction.class));
    }

    @Test
    public void testUpdateCompOffApproval_LeaveSummaryRepoFindTop1ByEmployeeIdAndLeaveTypeIdAndPeriodStartDtLessThanEqualAndPeriodEndDtGreaterThanEqualReturnsAbsent() throws Exception {
        // Setup
        // Configure CompOffTransactionRepo.findTop1ByIdAndStatus(...).
        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setEmployeeId(0L);
        compOffTransaction1.setPunchInTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction1.setPunchOutTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        compOffTransaction1.setApproverRemarks("comment");
        compOffTransaction1.setApprovedFor(EnumCompOffPeriod.HALF_DAY);
        compOffTransaction1.setTransactionStatus(EnumCompOffTransactionStatus.PENDING);
        compOffTransaction1.setCompOffUsageStatus(EnumCompOffUsageStatus.AVAILABLE);
        compOffTransaction1.setLastUpdatedDt(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setLastUpdatedBy("firstName");
        final Optional<CompOffTransaction> compOffTransaction = Optional.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findTop1ByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(compOffTransaction);

        // Configure BusinessValidationService.findEmployeeDetail(...).
        final EmployeeDetail employeeDetail = new EmployeeDetail();
        employeeDetail.setId(0L);
        employeeDetail.setEmpId("empId");
        employeeDetail.setFirstName("firstName");
        employeeDetail.setLastName("lastName");
        employeeDetail.setOfficialEmail("officialEmail");
        when(mockBusinessValidationService.findEmployeeDetail(0L)).thenReturn(employeeDetail);

        when(mockBusinessValidationService.findTimeDifference(Time.valueOf(LocalTime.of(0, 0, 0)),
                Time.valueOf(LocalTime.of(0, 0, 0)))).thenReturn(0L);

        // Configure LeaveTypeRepo.findByIdAndStatus(...).
        final LeaveType leaveType1 = new LeaveType();
        leaveType1.setId(0L);
        leaveType1.setName("name");
        leaveType1.setCode("code");
        leaveType1.setDescription("description");
        leaveType1.setLeavesAllowed(0.0);
        final Optional<LeaveType> leaveType = Optional.of(leaveType1);
        when(mockLeaveTypeRepo.findByIdAndStatus(0L, EnumStatus.ACTIVE)).thenReturn(leaveType);

        when(mockLeaveSummaryRepo.findTop1ByEmployeeIdAndLeaveTypeIdAndPeriodStartDtLessThanEqualAndPeriodEndDtGreaterThanEqual(
                eq(0L), any(LeaveType.class), eq(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0))),
                eq(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0))))).thenReturn(Optional.empty());

        // Run the test
        compOffTransactionServiceImplUnderTest.updateCompOffApproval("transactionId", "status", "compOffFor",
                "comment");

        // Verify the results
        verify(mockBusinessValidationService).validateWorkHours("punchIn", "punchOut", EnumCompOffPeriod.HALF_DAY, 0L);
        verify(mockCompOffTransactionRepo).save(any(CompOffTransaction.class));
        verify(mockLeaveSummaryRepo).save(any(LeaveSummary.class));
    }
}
