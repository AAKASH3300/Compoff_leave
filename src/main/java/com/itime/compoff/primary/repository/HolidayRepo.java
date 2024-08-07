package com.itime.compoff.primary.repository;

import com.itime.compoff.primary.entity.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public interface HolidayRepo extends JpaRepository<Holiday, Long> {
    List<Holiday> findAllByDate(Timestamp requestDate);
}



