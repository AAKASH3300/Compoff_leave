package com.itime.compoff.service;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.model.LeaveApplyRequest;
import com.itime.compoff.model.LeaveResponse;
import org.springframework.stereotype.Component;

@Component
public interface LeaveTransactionService {

    public LeaveResponse saveLeave(LeaveApplyRequest applyLeaverequest, String createdByEmpId) throws CommonException;

}