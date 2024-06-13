package com.itime.compoff.service.impl;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.mapper.LeaveMapper;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import com.itime.compoff.service.LeaveTransactionService;
import com.itime.compoff.validation.BusinessValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class LeaveTransactionServiceImpl implements LeaveTransactionService {

    @Autowired
    LeaveMapper leaveMapper;

    @Autowired
    BusinessValidationService businessValidationService;

    @Override
    public LeaveResponse saveLeave(LeaveApplyRequest applyLeaverequest, String createdByEmpId) throws CommonException {

        EmployeeDetail employeeDetail = businessValidationService.getEmployee(applyLeaverequest.getEmployeeId());

        return null;
        }
    }