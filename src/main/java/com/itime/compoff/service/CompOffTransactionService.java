package com.itime.compoff.service;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.model.CompOffApplyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public interface CompOffTransactionService {

    HttpStatus createCompOffTransaction(CompOffApplyRequest compOffApplyRequest) throws CommonException;

    void updateCompOffApproval(String transactionId, String status, String compOffFor) throws CommonException;

    void checkForExpiringCompOffs();
}
