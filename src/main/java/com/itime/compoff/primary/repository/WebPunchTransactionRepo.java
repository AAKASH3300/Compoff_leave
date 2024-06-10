package com.itime.compoff.primary.repository;

import com.itime.compoff.primary.entity.WebPunchTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Transactional(propagation = Propagation.REQUIRED)
public interface WebPunchTransactionRepo
        extends JpaRepository<WebPunchTransaction, Long>, JpaSpecificationExecutor<WebPunchTransaction> {


}

