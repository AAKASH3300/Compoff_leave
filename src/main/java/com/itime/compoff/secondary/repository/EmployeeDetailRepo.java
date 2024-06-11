package com.itime.compoff.secondary.repository;


import com.itime.compoff.secondary.entity.EmployeeDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional(propagation = Propagation.REQUIRED)
public interface EmployeeDetailRepo
        extends JpaRepository<EmployeeDetail, Long>, JpaSpecificationExecutor<EmployeeDetail> {

    Optional<EmployeeDetail> findByIdAndEmpStatus(Long id, Integer binary);

}
