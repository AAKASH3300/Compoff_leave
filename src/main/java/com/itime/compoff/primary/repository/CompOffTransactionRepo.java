package com.itime.compoff.primary.repository;

import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumCompOffUsageStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.primary.entity.CompOffTransaction;
import com.itime.compoff.primary.entity.LeaveTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface CompOffTransactionRepo extends JpaRepository<CompOffTransaction, Long>, JpaSpecificationExecutor<CompOffTransaction> {

    Optional<CompOffTransaction> findTop1ByIdAndStatus(long id, EnumStatus enumStatus);

    Optional<CompOffTransaction> findTopByEmployeeIdAndRequestedDateAndTransactionStatusIn(long employeeDetails, Timestamp timestamp, List<EnumCompOffTransactionStatus> pending);

    List<CompOffTransaction> findAllByExpiryDateBetween(Timestamp today, Timestamp endDate);

    List<CompOffTransaction> findAllByEmployeeIdAndRequestedDateBetweenAndTransactionStatusAndCompOffUsageStatusInAndStatusOrderByRequestedDateAsc(
            long employeeId, Timestamp startDate, Timestamp endDate, EnumCompOffTransactionStatus approved, List<EnumCompOffUsageStatus> available, EnumStatus status);

    List<CompOffTransaction> findAllByEmployeeIdAndTransactionStatusAndCompOffUsageStatusInAndStatusAndLeaveTransactionOrderByRequestedDateAsc
            (long employeeId, EnumCompOffTransactionStatus approved, List<EnumCompOffUsageStatus> used, EnumStatus active, LeaveTransaction leaveTransaction);
}
