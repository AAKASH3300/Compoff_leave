package com.itime.compoff.primary.repository;


import com.itime.compoff.primary.entity.LeaveSummary;
import com.itime.compoff.primary.entity.LeaveType;
import com.itime.compoff.secondary.entity.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.Optional;

@Transactional(propagation = Propagation.REQUIRED)
public interface LeaveSummaryRepo extends JpaRepository<LeaveSummary, Long>, JpaSpecificationExecutor<LeaveSummary> {

    Optional<LeaveSummary> findByLeaveTypeIdAndEmployeeId(LeaveType leaveType, EmployeeDetail employeeDetail);

    Optional<LeaveSummary> findTop1ByEmployeeIdAndLeaveTypeIdAndPeriodStartDtLessThanEqualAndPeriodEndDtGreaterThanEqual(
            long employee, LeaveType leaveType, Timestamp startDt, Timestamp endDt);

}

