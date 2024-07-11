package com.itime.compoff.primary.repository;


import com.itime.compoff.enumeration.EnumLeaveStatus;
import com.itime.compoff.enumeration.EnumStatus;
import com.itime.compoff.primary.entity.LeaveTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Transactional(propagation = Propagation.REQUIRED)
public interface LeaveTransactionRepo
        extends JpaRepository<LeaveTransaction, Long>, JpaSpecificationExecutor<LeaveTransaction> {

    Optional<LeaveTransaction> findTopByEmployeeIdAndStartDtAndEndDtAndLeaveStatusIn(long id, Timestamp startDt, Timestamp endDt, List<EnumLeaveStatus> status);

    LeaveTransaction findTopByEmployeeIdAndStatus(long id, EnumStatus enumStatus);
}

