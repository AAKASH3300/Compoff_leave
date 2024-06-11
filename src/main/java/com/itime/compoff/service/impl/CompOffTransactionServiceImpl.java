package com.itime.compoff.service.impl;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.mapper.CompOffMapper;
import com.itime.compoff.model.CompOffApplyRequest;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.repository.CompOffTransactionRepo;
import com.itime.compoff.primary.repository.LeaveApprovalRepo;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.service.CompOffTransactionService;
import com.itime.compoff.validation.BusinessValidationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class CompOffTransactionServiceImpl implements CompOffTransactionService {

    @Autowired
    CompOffTransactionRepo compOffTransactionRepo;

    @Autowired
    CompOffMapper compOffMapper;

    @Autowired
    BusinessValidationService businessValidationService;

    @Autowired
    LeaveApprovalRepo leaveApprovalRepo;

    @Override
    @Transactional(rollbackOn = CommonException.class)
    public HttpStatus createCompOffTransaction(CompOffApplyRequest compOffApplyRequest) throws CommonException {

        EmployeeDetail employeeDetails = businessValidationService.getEmployee(compOffApplyRequest.getEmployeeId().toString());

        CompOffTransaction compOffTransaction = compOffMapper.mapApplyRequestToEntity(employeeDetails, compOffApplyRequest);

        compOffTransactionRepo.save(compOffTransaction);

        return HttpStatus.OK;
    }
}
