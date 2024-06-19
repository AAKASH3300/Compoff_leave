package com.itime.compoff.service;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import org.springframework.stereotype.Component;

@Component
public interface LeaveTransactionService {

     LeaveResponse saveLeave(LeaveApplyRequest applyLeaveRequest, String createdByEmpId) throws CommonException;

     void updateLeaveTransaction(String transactionId, String status, String employeeId, String comment) throws CommonException;
}