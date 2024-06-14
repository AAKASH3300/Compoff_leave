package com.itime.compoff.primary.repository;

import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface CompOffTransactionRepo extends JpaRepository<CompOffTransaction, Long>, JpaSpecificationExecutor<CompOffTransaction> {

    @Query("SELECT c FROM CompOffTransaction c WHERE c.requestedDt BETWEEN :startDate AND :endDate")
    List<CompOffTransaction> findExpiringCompOffs(@Param("startDate") Timestamp startDate, @Param("endDate") Timestamp endDate);

    Optional<CompOffTransaction> findTop1ByIdAndStatus(long id, EnumStatus enumStatus);

    Optional<CompOffTransaction> findTopByEmployeeIdAndRequestedDtAndTransactionStatusIn(EmployeeDetail employeeDetails, Timestamp timestamp, List<EnumCompOffTransactionStatus> pending);
}
