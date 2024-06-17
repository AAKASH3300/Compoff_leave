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
import com.itime.compoff.primary.entity.LeaveTransaction;
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
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
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

        log.trace("Finding Employee Detail");
        return employeeDetailRepo.findByIdAndEmpStatus(id, EnumStatus.ACTIVE.getBinary())
                .orElseThrow(() -> new DataNotFoundException("Employee not found"));
    }

    public void duplicateCompOffValidation(EmployeeDetail employeeDetails, CompOffApplyRequest compOffApplyRequest) throws CommonException {

        log.trace("Validating Compoff Duplication");
        Optional<CompOffTransaction> compOffTransaction = compOffTransactionRepo.findTopByEmployeeIdAndRequestedDtAndTransactionStatusIn(employeeDetails.getId(), Timestamp.valueOf(compOffApplyRequest.getRequestedDate()), List.of(EnumCompOffTransactionStatus.PENDING, EnumCompOffTransactionStatus.APPROVED));
        if (compOffTransaction.isPresent()) {
            throw new DataNotFoundException(ErrorMessages.ALREADY_REQUEST_RAISED);
        }
    }

    public void validateWorkHours(String punchIn, String punchOut, EnumCompOffPeriod compOffPeriod, Long actualWorkHour) throws CommonException {

        log.trace("Validating Work Hours");
        AtomicInteger minWorkHour = new AtomicInteger(AppConstants.MIN_WORK_HOUR_COMPOFF);
        AtomicInteger fullDayCompOff = new AtomicInteger(AppConstants.MIN_WORK_HOUR_FULL_DAY_COMPOFF);

        long requiredMinWorkHours = DateTimeUtils.findMillisecondsFromHourAndMinute(minWorkHour.get(), 0);
        long actualMilliSeconds = actualWorkHour != null ? actualWorkHour : this.findTimeDifference(Time.valueOf(punchIn.concat(AppConstants.TIME_CONCAT)), Time.valueOf(punchOut.concat(AppConstants.TIME_CONCAT)));
        if (actualMilliSeconds < requiredMinWorkHours) {
            throw new CommonException(String.format(ErrorMessages.WORK_HOUR_NOT_ENOUGH_COMPOFF, minWorkHour.get()));
        }
        if (compOffPeriod.equals(EnumCompOffPeriod.FULL_DAY) && (actualMilliSeconds < DateTimeUtils.findMillisecondsFromHourAndMinute(fullDayCompOff.get(), 0))) {
            throw new CommonException(String.format(ErrorMessages.WORK_HOUR_NOT_ENOUGH_FULLDAY_COMPOFF, fullDayCompOff.get()));
        }
    }

    public Long findTimeDifference(Time fromTime, Time toTime) {

        LocalDateTime fromDate = DateTimeUtils.convertTimeToLocalDateTime(fromTime);
        LocalDateTime toDate = DateTimeUtils.convertTimeToLocalDateTime(toTime);
        if (fromDate.isAfter(toDate)) {
            toDate = toDate.plusDays(1);
        }
        return ChronoUnit.MILLIS.between(fromDate, toDate);
    }

    public void duplicateLeaveCheck(EmployeeDetail employee, LeaveApplyRequest leaveRequest) throws DataNotFoundException {

        log.trace("Validating Leave Duplication");
        Optional<LeaveTransaction> listLeaveTransaction = leaveTransactionRepo.findTopByEmployeeIdAndStartDtAndEndDtAndLeaveStatusIn(employee.getId(), Timestamp.valueOf(leaveRequest.getStartDt()), Timestamp.valueOf(leaveRequest.getEndDt()), List.of(EnumLeaveStatus.PENDING, EnumLeaveStatus.APPROVED));

        if (listLeaveTransaction.isPresent()) {
            throw new DataNotFoundException(ErrorMessages.ALREADY_REQUEST_RAISED);
        }

    }

}
