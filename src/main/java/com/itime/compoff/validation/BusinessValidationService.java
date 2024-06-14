package com.itime.compoff.validation;


import com.itime.compoff.controller.CompOffTransactionController;
import com.itime.compoff.enumeration.EnumCompOffPeriod;
import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
import com.itime.compoff.exception.ErrorMessages;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.secondary.repository.EmployeeDetailRepo;
import com.itime.compoff.utils.DateTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class BusinessValidationService {

    private static final Logger log = LoggerFactory.getLogger(BusinessValidationService.class);

    @Autowired
    CompOffTransactionRepo compOffTransactionRepo;

    @Autowired
    EmployeeDetailRepo employeeDetailRepo;

    public EmployeeDetail getEmployee(String employee) throws DataNotFoundException {
        log.trace("Getting employee details");
        return employeeDetailRepo
                .findByIdAndEmpStatus(Long.valueOf(employee), Integer.valueOf(EnumStatus.ACTIVE.getBinary()))
                .orElseThrow(() -> new DataNotFoundException(ErrorMessages.EMPLOYEE_ID_NOT_FOUND,
                        HttpStatus.BAD_REQUEST.value()));
    }

    public void duplicateCompOffValidation(EmployeeDetail employeeDetails, CompOffApplyRequest compOffApplyRequest) throws CommonException {
            Optional<CompOffTransaction> compOffTransaction =
                    compOffTransactionRepo.findTopByEmployeeIdAndRequestedDtAndTransactionStatusIn(
                    employeeDetails, DateTimeUtils.convertFromJsonSqlDateOnly(compOffApplyRequest.getRequestedDate()),
                    List.of(EnumCompOffTransactionStatus.PENDING, EnumCompOffTransactionStatus.APPROVED));
            if (compOffTransaction.isPresent()) {
                throw new CommonException(ErrorMessages.ALREADY_REQUEST_RAISED, HttpStatus.BAD_REQUEST.value());
            }
        }


}
