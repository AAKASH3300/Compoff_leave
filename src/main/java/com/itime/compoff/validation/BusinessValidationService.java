package com.itime.compoff.validation;


import com.itime.compoff.enumeration.EnumCompOffPeriod;
import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumLeaveStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
import com.itime.compoff.exception.ErrorMessages;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.entity.LeaveSummary;
import com.itime.compoff.primary.entity.LeaveTransaction;
import com.itime.compoff.primary.entity.LeaveType;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
import com.itime.compoff.primary.repository.LeaveTransactionRepo;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.secondary.repository.EmployeeDetailRepo;
import com.itime.compoff.utils.AppConstants;
import com.itime.compoff.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class BusinessValidationService {

    private static final Logger log = LoggerFactory.getLogger(BusinessValidationService.class);

    @Autowired
    CompOffTransactionRepo compOffTransactionRepo;

    @Autowired
    EmployeeDetailRepo employeeDetailRepo;

    @Autowired
    LeaveTransactionRepo leaveTransactionRepo;


    public EmployeeDetail findEmployeeDetail(long id) throws DataNotFoundException {

        log.trace("Finding Employee Detail......");
        return employeeDetailRepo.findByIdAndEmpStatus(id, EnumStatus.ACTIVE.getBinary())
                .orElseThrow(() -> new DataNotFoundException("Employee not found"));
    }

    public void duplicateCompOffValidation(EmployeeDetail employeeDetails, CompOffApplyRequest compOffApplyRequest) throws CommonException {

        log.trace("Validating Duplicate Compoff......");
        Optional<CompOffTransaction> compOffTransaction = compOffTransactionRepo.findTopByEmployeeIdAndRequestedDtAndTransactionStatusIn(employeeDetails.getId(), Timestamp.valueOf(compOffApplyRequest.getRequestedDate()), List.of(EnumCompOffTransactionStatus.PENDING, EnumCompOffTransactionStatus.APPROVED));
        if (compOffTransaction.isPresent()) {
            throw new CommonException(ErrorMessages.ALREADY_REQUEST_RAISED);
        }
    }

    public void validateCompOff(CompOffApplyRequest compOffApplyRequest, Timestamp requestDate) throws CommonException {

        if (requestDate.toLocalDateTime().toLocalDate().isAfter(LocalDate.now())) {
            throw new CommonException(ErrorMessages.INVALID_COMPOFF_REQUEST_DATE);
        }
        this.validateWorkHours(compOffApplyRequest.getPunchIn(), compOffApplyRequest.getPunchOut(),
                EnumCompOffPeriod.valuesOf(compOffApplyRequest.getRequestedFor()), null);
    }

    public void validateWorkHours(String punchIn, String punchOut, EnumCompOffPeriod compOffPeriod, Long actualWorkHour) throws CommonException {

        log.trace("Validating Work Hours.....");
        AtomicInteger minWorkHour = new AtomicInteger(AppConstants.MIN_WORK_HOUR_COMPOFF);
        AtomicInteger fullDayCompOff = new AtomicInteger(AppConstants.MIN_WORK_HOUR_FULL_DAY_COMPOFF);

        long requiredMinWorkHours = DateTimeUtils.findMillisecondsFromHourAndMinute(minWorkHour.get(), 0);
        long actualMilliSeconds = actualWorkHour != null ? actualWorkHour : this.findTimeDifference(Time.valueOf(punchIn), Time.valueOf(punchOut));
        if (actualMilliSeconds < requiredMinWorkHours) {
            throw new CommonException(String.format(ErrorMessages.WORK_HOUR_NOT_ENOUGH_COMPOFF, minWorkHour.get()));
        }
        if (compOffPeriod.equals(EnumCompOffPeriod.FULL_DAY) && (actualMilliSeconds < DateTimeUtils.findMillisecondsFromHourAndMinute(fullDayCompOff.get(), 0))) {
            throw new CommonException(String.format(ErrorMessages.WORK_HOUR_NOT_ENOUGH_FULLDAY_COMPOFF, fullDayCompOff.get()));
        }
    }

    public Long findTimeDifference(Time fromTime, Time toTime) {

        log.trace("Finding time difference......");
        LocalDateTime fromDate = DateTimeUtils.convertTimeToLocalDateTime(fromTime);
        LocalDateTime toDate = DateTimeUtils.convertTimeToLocalDateTime(toTime);
        if (fromDate.isAfter(toDate)) {
            toDate = toDate.plusDays(1);
        }
        return ChronoUnit.MILLIS.between(fromDate, toDate);
    }

    public void duplicateLeaveCheck(EmployeeDetail employee, LeaveApplyRequest leaveRequest) throws CommonException {

        log.trace("Validating Leave Duplication.......");
        Optional<LeaveTransaction> listLeaveTransaction = leaveTransactionRepo.findTopByEmployeeIdAndStartDtAndEndDtAndLeaveStatusIn(employee.getId(), Timestamp.valueOf(leaveRequest.getStartDt()), Timestamp.valueOf(leaveRequest.getEndDt()), List.of(EnumLeaveStatus.PENDING, EnumLeaveStatus.APPROVED));

        if (listLeaveTransaction.isPresent()) {
            throw new CommonException(ErrorMessages.ALREADY_REQUEST_RAISED);
        }

    }

    public void validateAvailableLeave(Optional<LeaveSummary> leaveSummary, Double numberOfDays) throws CommonException {

        log.trace("Checking leave available");
        if (leaveSummary.isPresent() && leaveSummary.get().getLeavesAvailable() < numberOfDays) {
            throw new CommonException(AppConstants.LEAVE_UNAVAILABLE);
        }
    }

    public void validateCompOffLeave(LeaveType leaveType, LeaveApplyRequest applyLeaveRequest) throws CommonException {
        String compOffLeaveCode = AppConstants.DEFAULT_COMP_OFF_LEAVE_CODE;
        if (compOffLeaveCode.equals(leaveType.getCode()) &&
               Timestamp.valueOf(applyLeaveRequest.getStartDt()).before(new Date())) {
            throw new CommonException(ErrorMessages.INVALID_COMPOFF_TIME_RANGE);
        }
    }

}
