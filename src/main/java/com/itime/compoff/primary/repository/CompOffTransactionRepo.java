package com.itime.compoff.primary.repository;

import com.itime.compoff.primary.entity.CompOffTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface CompOffTransactionRepo extends JpaRepository<CompOffTransaction, Long>, JpaSpecificationExecutor<CompOffTransaction> {

    @Query("SELECT c FROM CompOffTransaction c WHERE c.requestedDt BETWEEN :startDate AND :endDate")
    List<CompOffTransaction> findExpiringCompOffs(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

}
