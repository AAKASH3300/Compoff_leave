package com.itime.compoff.service;

import com.itime.compoff.model.CompOffApplyRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public interface CompOffTransactionService {

    HttpStatus createCompOffTransaction(CompOffApplyRequest compOffApplyRequest);

}
