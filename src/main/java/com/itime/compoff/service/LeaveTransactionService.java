package com.itime.compoff.service;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import org.springframework.stereotype.Component;

@Component
public interface LeaveTransactionService {

    public LeaveResponse saveLeave(LeaveApplyRequest applyLeaverequest, String createdByEmpId) throws CommonException;

    public void updateLeave(String transactionId, String status, String employeeId,String comment) throws CommonException;
}