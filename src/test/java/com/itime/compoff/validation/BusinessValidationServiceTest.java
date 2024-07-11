package com.itime.compoff.validation;

import com.itime.compoff.enumeration.EnumCompOffPeriod;
import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumLeaveStatus;
import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.primary.entity.*;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
import com.itime.compoff.primary.repository.LeaveTransactionRepo;
import com.itime.compoff.primary.repository.ShiftRosterRepo;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.secondary.repository.EmployeeDetailRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BusinessValidationServiceTest {

    @Mock
    private CompOffTransactionRepo mockCompOffTransactionRepo;
    @Mock
    private EmployeeDetailRepo mockEmployeeDetailRepo;
    @Mock
    private LeaveTransactionRepo mockLeaveTransactionRepo;
    @Mock
    private ShiftRosterRepo mockShiftRosterRepo;

    private BusinessValidationService businessValidationServiceUnderTest;

    @Before
    public void setUp() {
        businessValidationServiceUnderTest = new BusinessValidationService();
        businessValidationServiceUnderTest.compOffTransactionRepo = mockCompOffTransactionRepo;
        businessValidationServiceUnderTest.employeeDetailRepo = mockEmployeeDetailRepo;
        businessValidationServiceUnderTest.leaveTransactionRepo = mockLeaveTransactionRepo;
        businessValidationServiceUnderTest.shiftRosterRepo = mockShiftRosterRepo;
    }

    @Test
    public void testFindEmployeeDetail() throws Exception {
        // Setup
        // Configure EmployeeDetailRepo.findByIdAndEmpStatus(...).
        final EmployeeDetail employeeDetail1 = new EmployeeDetail();
        employeeDetail1.setId(0L);
        employeeDetail1.setEmpId("empId");
        employeeDetail1.setFirstName("firstName");
        employeeDetail1.setLastName("lastName");
        employeeDetail1.setOfficialEmail("officialEmail");
        final Optional<EmployeeDetail> employeeDetail = Optional.of(employeeDetail1);
        when(mockEmployeeDetailRepo.findByIdAndEmpStatus(0L, 0)).thenReturn(employeeDetail);

        // Run the test
        final EmployeeDetail result = businessValidationServiceUnderTest.findEmployeeDetail(0L);

        // Verify the results
    }

    @Test
    public void testFindEmployeeDetail_EmployeeDetailRepoReturnsAbsent() {
        // Setup
        when(mockEmployeeDetailRepo.findByIdAndEmpStatus(0L, 0)).thenReturn(Optional.empty());

        // Run the test
        assertThrows(DataNotFoundException.class, () -> businessValidationServiceUnderTest.findEmployeeDetail(0L));
    }

    @Test
    public void testDuplicateCompOffValidation_ThrowsCommonException() {
        // Setup
        final EmployeeDetail employeeDetails = new EmployeeDetail();
        employeeDetails.setId(0L);
        employeeDetails.setEmpId("empId");
        employeeDetails.setFirstName("firstName");
        employeeDetails.setLastName("lastName");
        employeeDetails.setOfficialEmail("officialEmail");

        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setEmployeeId(0L);
        compOffApplyRequest.setRequestedDate("requestedDate");
        compOffApplyRequest.setPunchIn("punchIn");
        compOffApplyRequest.setPunchOut("punchOut");
        compOffApplyRequest.setRequestedFor("requestedFor");

        // Configure CompOffTransactionRepo.findTopByEmployeeIdAndRequestedDateAndTransactionStatusIn(...).
        final CompOffTransaction compOffTransaction1 = new CompOffTransaction();
        compOffTransaction1.setId(0L);
        compOffTransaction1.setEmployeeId(0L);
        compOffTransaction1.setRequestedDate(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setExpiryDate(Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));
        compOffTransaction1.setPunchInTime(Time.valueOf(LocalTime.of(0, 0, 0)));
        final Optional<CompOffTransaction> compOffTransaction = Optional.of(compOffTransaction1);
        when(mockCompOffTransactionRepo.findTopByEmployeeIdAndRequestedDateAndTransactionStatusIn(0L,
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                List.of(EnumCompOffTransactionStatus.PENDING))).thenReturn(compOffTransaction);

        // Run the test
        assertThrows(CommonException.class,
                () -> businessValidationServiceUnderTest.duplicateCompOffValidation(employeeDetails,
                        compOffApplyRequest));
    }

    @Test
    public void testDuplicateCompOffValidation_CompOffTransactionRepoReturnsAbsent() throws Exception {
        // Setup
        final EmployeeDetail employeeDetails = new EmployeeDetail();
        employeeDetails.setId(0L);
        employeeDetails.setEmpId("empId");
        employeeDetails.setFirstName("firstName");
        employeeDetails.setLastName("lastName");
        employeeDetails.setOfficialEmail("officialEmail");

        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setEmployeeId(0L);
        compOffApplyRequest.setRequestedDate("requestedDate");
        compOffApplyRequest.setPunchIn("punchIn");
        compOffApplyRequest.setPunchOut("punchOut");
        compOffApplyRequest.setRequestedFor("requestedFor");

        when(mockCompOffTransactionRepo.findTopByEmployeeIdAndRequestedDateAndTransactionStatusIn(0L,
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                List.of(EnumCompOffTransactionStatus.PENDING))).thenReturn(Optional.empty());

        // Run the test
        businessValidationServiceUnderTest.duplicateCompOffValidation(employeeDetails, compOffApplyRequest);

        // Verify the results
    }

    @Test
    public void testValidateCompOff() throws Exception {
        // Setup
        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setEmployeeId(0L);
        compOffApplyRequest.setRequestedDate("requestedDate");
        compOffApplyRequest.setPunchIn("punchIn");
        compOffApplyRequest.setPunchOut("punchOut");
        compOffApplyRequest.setRequestedFor("requestedFor");

        // Configure ShiftRosterRepo.findByEmployeeId(...).
        final ShiftRoster shiftRoster = new ShiftRoster();
        shiftRoster.setDay1(1);
        shiftRoster.setDay2(1);
        shiftRoster.setDay3(1);
        shiftRoster.setDay4(1);
        shiftRoster.setDay5(1);
        shiftRoster.setDay6(1);
        shiftRoster.setDay7(1);
        shiftRoster.setDay8(1);
        shiftRoster.setDay9(1);
        shiftRoster.setDay10(1);
        shiftRoster.setDay11(1);
        shiftRoster.setDay12(1);
        shiftRoster.setDay13(1);
        shiftRoster.setDay14(1);
        shiftRoster.setDay15(1);
        shiftRoster.setDay16(1);
        shiftRoster.setDay17(1);
        shiftRoster.setDay18(1);
        shiftRoster.setDay19(1);
        shiftRoster.setDay20(1);
        shiftRoster.setDay21(1);
        shiftRoster.setDay22(1);
        shiftRoster.setDay23(1);
        shiftRoster.setDay24(1);
        shiftRoster.setDay25(1);
        shiftRoster.setDay26(1);
        shiftRoster.setDay27(1);
        shiftRoster.setDay28(1);
        shiftRoster.setDay29(1);
        shiftRoster.setDay30(1);
        shiftRoster.setDay31(1);
        when(mockShiftRosterRepo.findByEmployeeId(0L)).thenReturn(shiftRoster);

        // Run the test
        businessValidationServiceUnderTest.validateCompOff(compOffApplyRequest,
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));

        // Verify the results
    }

    @Test
    public void testValidateCompOff_ShiftRosterRepoReturnsNull() {
        // Setup
        final CompOffApplyRequest compOffApplyRequest = new CompOffApplyRequest();
        compOffApplyRequest.setEmployeeId(0L);
        compOffApplyRequest.setRequestedDate("requestedDate");
        compOffApplyRequest.setPunchIn("punchIn");
        compOffApplyRequest.setPunchOut("punchOut");
        compOffApplyRequest.setRequestedFor("requestedFor");

        when(mockShiftRosterRepo.findByEmployeeId(0L)).thenReturn(null);

        // Run the test
        assertThrows(CommonException.class,
                () -> businessValidationServiceUnderTest.validateCompOff(compOffApplyRequest,
                        Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0))));
    }

    @Test
    public void testGetShiftValue() {

        final ShiftRoster shiftRoster = new ShiftRoster();
        shiftRoster.setDay1(1);
        shiftRoster.setDay2(1);
        shiftRoster.setDay3(1);
        shiftRoster.setDay4(1);
        shiftRoster.setDay5(1);
        shiftRoster.setDay6(1);
        shiftRoster.setDay7(1);
        shiftRoster.setDay8(1);
        shiftRoster.setDay9(1);
        shiftRoster.setDay10(1);
        shiftRoster.setDay11(1);
        shiftRoster.setDay12(1);
        shiftRoster.setDay13(1);
        shiftRoster.setDay14(1);
        shiftRoster.setDay15(1);
        shiftRoster.setDay16(1);
        shiftRoster.setDay17(1);
        shiftRoster.setDay18(1);
        shiftRoster.setDay19(1);
        shiftRoster.setDay20(1);
        shiftRoster.setDay21(1);
        shiftRoster.setDay22(1);
        shiftRoster.setDay23(1);
        shiftRoster.setDay24(1);
        shiftRoster.setDay25(1);
        shiftRoster.setDay26(1);
        shiftRoster.setDay27(1);
        shiftRoster.setDay28(1);
        shiftRoster.setDay29(1);
        shiftRoster.setDay30(1);
        shiftRoster.setDay31(1);

        final Integer result = businessValidationServiceUnderTest.getShiftValue(shiftRoster,
                Timestamp.valueOf(LocalDateTime.of(2024, 7, 1, 0, 0, 0, 0)));

        assertEquals(Integer.valueOf(1), result);
    }

    @Test
    public void testValidateWorkHours() throws Exception {

        businessValidationServiceUnderTest.validateWorkHours("11:00:00", "19:00:00", EnumCompOffPeriod.HALF_DAY, 0L);
    }

    @Test
    public void testValidateWorkHours_ThrowsCommonException() {
        assertThrows(CommonException.class,
                () -> businessValidationServiceUnderTest.validateWorkHours("punchIn", "punchOut",
                        EnumCompOffPeriod.HALF_DAY, 0L));
    }

    @Test
    public void testFindTimeDifference() {
        assertEquals(Long.valueOf(0L),
                businessValidationServiceUnderTest.findTimeDifference(Time.valueOf(LocalTime.of(0, 0, 0)),
                        Time.valueOf(LocalTime.of(0, 0, 0))));
    }

    @Test
    public void testDuplicateLeaveCheck_ThrowsCommonException() {
        // Setup
        final EmployeeDetail employee = new EmployeeDetail();
        employee.setId(0L);
        employee.setEmpId("empId");
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setOfficialEmail("officialEmail");

        final LeaveApplyRequest leaveRequest = new LeaveApplyRequest();
        leaveRequest.setEmployeeId("employeeId");
        leaveRequest.setLeaveTypeId("leaveTypeId");
        leaveRequest.setStartDt("startDt");
        leaveRequest.setEndDt("endDt");
        leaveRequest.setNoOfDays("noOfDays");

        // Configure LeaveTransactionRepo.findTopByEmployeeIdAndStartDtAndEndDtAndLeaveStatusIn(...).
        final LeaveTransaction leaveTransaction1 = new LeaveTransaction();
        leaveTransaction1.setId(0L);
        leaveTransaction1.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveTypeId.setName("name");
        leaveTransaction1.setLeaveTypeId(leaveTypeId);
        final Optional<LeaveTransaction> leaveTransaction = Optional.of(leaveTransaction1);
        when(mockLeaveTransactionRepo.findTopByEmployeeIdAndStartDtAndEndDtAndLeaveStatusIn(0L,
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                List.of(EnumLeaveStatus.PENDING))).thenReturn(leaveTransaction);

        // Run the test
        assertThrows(CommonException.class,
                () -> businessValidationServiceUnderTest.duplicateLeaveCheck(employee, leaveRequest));
    }

    @Test
    public void testDuplicateLeaveCheck_LeaveTransactionRepoReturnsAbsent() throws Exception {
        // Setup
        final EmployeeDetail employee = new EmployeeDetail();
        employee.setId(0L);
        employee.setEmpId("empId");
        employee.setFirstName("firstName");
        employee.setLastName("lastName");
        employee.setOfficialEmail("officialEmail");

        final LeaveApplyRequest leaveRequest = new LeaveApplyRequest();
        leaveRequest.setEmployeeId("employeeId");
        leaveRequest.setLeaveTypeId("leaveTypeId");
        leaveRequest.setStartDt("startDt");
        leaveRequest.setEndDt("endDt");
        leaveRequest.setNoOfDays("noOfDays");

        when(mockLeaveTransactionRepo.findTopByEmployeeIdAndStartDtAndEndDtAndLeaveStatusIn(0L,
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                List.of(EnumLeaveStatus.PENDING))).thenReturn(Optional.empty());

        // Run the test
        businessValidationServiceUnderTest.duplicateLeaveCheck(employee, leaveRequest);

        // Verify the results
    }

    @Test
    public void testValidateAvailableLeave() throws Exception {

        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(0L);
        leaveSummary1.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveSummary1.setLeaveTypeId(leaveTypeId);
        leaveSummary1.setLeavesAvailable(0.0);
        final Optional<LeaveSummary> leaveSummary = Optional.of(leaveSummary1);


        businessValidationServiceUnderTest.validateAvailableLeave(leaveSummary, 0.0);

    }

    @Test
    public void testValidateAvailableLeave_ThrowsCommonException() {
        // Setup
        final LeaveSummary leaveSummary1 = new LeaveSummary();
        leaveSummary1.setId(0L);
        leaveSummary1.setEmployeeId(0L);
        final LeaveType leaveTypeId = new LeaveType();
        leaveTypeId.setId(0L);
        leaveSummary1.setLeaveTypeId(leaveTypeId);
        leaveSummary1.setLeavesAvailable(0.0);
        final Optional<LeaveSummary> leaveSummary = Optional.of(leaveSummary1);

        // Run the test
        assertThrows(CommonException.class,
                () -> businessValidationServiceUnderTest.validateAvailableLeave(leaveSummary, 0.0));
    }

    @Test
    public void testValidateCompOffLeave() throws Exception {

        final LeaveType leaveType = new LeaveType();
        leaveType.setId(0L);
        leaveType.setName("name");
        leaveType.setCode("code");
        leaveType.setDescription("description");
        leaveType.setLeavesAllowed(0.0);

        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("employeeId");
        applyLeaveRequest.setLeaveTypeId("leaveTypeId");
        applyLeaveRequest.setStartDt("startDt");
        applyLeaveRequest.setEndDt("endDt");
        applyLeaveRequest.setNoOfDays("noOfDays");

        businessValidationServiceUnderTest.validateCompOffLeave(leaveType, applyLeaveRequest);

    }

    @Test
    public void testValidateCompOffLeave_ThrowsCommonException() {

        final LeaveType leaveType = new LeaveType();
        leaveType.setId(1L);
        leaveType.setName("Compoff");
        leaveType.setDescription("Compensatoy off");
        leaveType.setLeavesAllowed(12.0);

        final LeaveApplyRequest applyLeaveRequest = new LeaveApplyRequest();
        applyLeaveRequest.setEmployeeId("1");
        applyLeaveRequest.setLeaveTypeId("2");
        applyLeaveRequest.setStartDt("2024-07-12 00:00:00");
        applyLeaveRequest.setEndDt("2024-07-12 00:00:00");
        applyLeaveRequest.setNoOfDays("1.0");

        assertThrows(CommonException.class,
                () -> businessValidationServiceUnderTest.validateCompOffLeave(leaveType, applyLeaveRequest));
    }

    @Test
    public void testCountShiftDays() {

        final ShiftRoster shiftRoster = new ShiftRoster();
        shiftRoster.setDay1(1);
        shiftRoster.setDay2(1);
        shiftRoster.setDay3(1);
        shiftRoster.setDay4(1);
        shiftRoster.setDay5(1);
        shiftRoster.setDay6(1);
        shiftRoster.setDay7(1);
        shiftRoster.setDay8(1);
        shiftRoster.setDay9(1);
        shiftRoster.setDay10(1);
        shiftRoster.setDay11(1);
        shiftRoster.setDay12(1);
        shiftRoster.setDay13(1);
        shiftRoster.setDay14(1);
        shiftRoster.setDay15(1);
        shiftRoster.setDay16(1);
        shiftRoster.setDay17(1);
        shiftRoster.setDay18(1);
        shiftRoster.setDay19(1);
        shiftRoster.setDay20(1);
        shiftRoster.setDay21(1);
        shiftRoster.setDay22(1);
        shiftRoster.setDay23(1);
        shiftRoster.setDay24(1);
        shiftRoster.setDay25(1);
        shiftRoster.setDay26(1);
        shiftRoster.setDay27(1);
        shiftRoster.setDay28(1);
        shiftRoster.setDay29(1);
        shiftRoster.setDay30(1);
        shiftRoster.setDay31(1);

        final int result = businessValidationServiceUnderTest.countShiftDays(shiftRoster,
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)),
                Timestamp.valueOf(LocalDateTime.of(2020, 1, 1, 0, 0, 0, 0)));

        assertEquals(0, result);
    }
}
