package com.itime.compoff.primary.entity;

import com.itime.compoff.enumeration.EnumStatus;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

public class HolidayTest {

    @Test
     void testHolidayEntity() {

        Holiday holiday = new Holiday();

        holiday.setId(1L);
        holiday.setName("New Year");
        holiday.setDate(Timestamp.valueOf("2024-01-01 00:00:00"));
        holiday.setStatus(EnumStatus.ACTIVE);
        holiday.setCreatedDt(Timestamp.valueOf("2023-12-01 00:00:00"));
        holiday.setCreatedBy("admin");
        holiday.setLastUpdatedDt(Timestamp.valueOf("2023-12-01 00:00:00"));
        holiday.setLastUpdatedBy("admin");
        holiday.setDescription("New Year Holiday");


        Holiday referenceHoliday = new Holiday();

        referenceHoliday.setId(2L);
        holiday.setReferenceHolidayId(referenceHoliday);
        assertThat(holiday.getId()).isEqualTo(1L);
        assertThat(holiday.getName()).isEqualTo("New Year");
        assertThat(holiday.getDate()).isEqualTo(Timestamp.valueOf("2024-01-01 00:00:00"));
        assertThat(holiday.getStatus()).isEqualTo(EnumStatus.ACTIVE);
        assertThat(holiday.getCreatedDt()).isEqualTo(Timestamp.valueOf("2023-12-01 00:00:00"));
        assertThat(holiday.getCreatedBy()).isEqualTo("admin");
        assertThat(holiday.getLastUpdatedDt()).isEqualTo(Timestamp.valueOf("2023-12-01 00:00:00"));
        assertThat(holiday.getLastUpdatedBy()).isEqualTo("admin");
        assertThat(holiday.getDescription()).isEqualTo("New Year Holiday");
        assertThat(holiday.getReferenceHolidayId()).isEqualTo(referenceHoliday);
    }

}
