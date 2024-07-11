package com.itime.compoff.primary.repository;

import com.itime.compoff.primary.entity.ShiftRoster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface ShiftRosterRepo extends JpaRepository<ShiftRoster, Long>, JpaSpecificationExecutor<ShiftRoster> {

    ShiftRoster findByEmployeeId(Long employeeId);

    ShiftRoster findByEmployeeIdAndMonthGreaterThanEqualAndMonthLessThanEqualAndYearGreaterThanEqualAndYearLessThanEqual(long employeeDetail, int monthValue, int monthValue1, int year, int year1);
}
