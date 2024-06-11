package com.itime.compoff.validation;


import com.itime.compoff.controller.CompOffTransactionController;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.exception.DataNotFoundException;
import com.itime.compoff.exception.ErrorMessages;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.secondary.repository.EmployeeDetailRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class BusinessValidationService {

    private static final Logger log = LoggerFactory.getLogger(BusinessValidationService.class);

    @Autowired
    EmployeeDetailRepo employeeDetailRepo;

    public void duplicateCompOffValidation(EmployeeDetail employeeDetails, CompOffApplyRequest compOffApplyRequest) {

    }

    public EmployeeDetail getEmployee(String employee) throws DataNotFoundException {
        log.trace("Getting employee details");
        return employeeDetailRepo
                .findByIdAndEmpStatus(Long.valueOf(employee), Integer.valueOf(EnumStatus.ACTIVE.getBinary()))
                .orElseThrow(() -> new DataNotFoundException(ErrorMessages.EMPLOYEE_ID_NOT_FOUND,
                        HttpStatus.BAD_REQUEST.value()));
    }
}
