package com.itime.compoff.service;

import com.itime.compoff.model.LeaveApplyRequest;
import org.springframework.http.HttpStatus;

public interface LeaveTransactionService {
    HttpStatus applyLeave(LeaveApplyRequest leaveApplyRequest);
}
