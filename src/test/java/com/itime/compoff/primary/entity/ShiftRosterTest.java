package com.itime.compoff.primary.entity;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;

public class ShiftRosterTest {

    @Test
     void testShiftRosterEntity() {
        ShiftRoster shiftRoster = new ShiftRoster();

        shiftRoster.setId(1L);
        shiftRoster.setEmployeeId(12345L);
        shiftRoster.setDay1(1);
        shiftRoster.setDay2(2);
        shiftRoster.setDay3(3);
        shiftRoster.setDay4(4);
        shiftRoster.setDay5(5);
        shiftRoster.setDay6(6);
        shiftRoster.setDay7(7);
        shiftRoster.setDay8(8);
        shiftRoster.setDay9(9);
        shiftRoster.setDay10(10);
        shiftRoster.setDay11(11);
        shiftRoster.setDay12(12);
        shiftRoster.setDay13(13);
        shiftRoster.setDay14(14);
        shiftRoster.setDay15(15);
        shiftRoster.setDay16(16);
        shiftRoster.setDay17(17);
        shiftRoster.setDay18(18);
        shiftRoster.setDay19(19);
        shiftRoster.setDay20(20);
        shiftRoster.setDay21(21);
        shiftRoster.setDay22(22);
        shiftRoster.setDay23(23);
        shiftRoster.setDay24(24);
        shiftRoster.setDay25(25);
        shiftRoster.setDay26(26);
        shiftRoster.setDay27(27);
        shiftRoster.setDay28(28);
        shiftRoster.setDay29(29);
        shiftRoster.setDay30(30);
        shiftRoster.setDay31(31);
        shiftRoster.setMonth(7);
        shiftRoster.setYear(2024);
        shiftRoster.setCreatedDt(Timestamp.valueOf("2024-07-01 00:00:00"));
        shiftRoster.setCreatedBy("admin");
        shiftRoster.setLastUpdatedDt(Timestamp.valueOf("2024-07-02 00:00:00"));
        shiftRoster.setLastUpdatedBy("admin");

        assertThat(shiftRoster.getId()).isEqualTo(1L);
        assertThat(shiftRoster.getEmployeeId()).isEqualTo(12345L);
        assertThat(shiftRoster.getDay1()).isEqualTo(1);
        assertThat(shiftRoster.getDay2()).isEqualTo(2);
        assertThat(shiftRoster.getDay3()).isEqualTo(3);
        assertThat(shiftRoster.getDay4()).isEqualTo(4);
        assertThat(shiftRoster.getDay5()).isEqualTo(5);
        assertThat(shiftRoster.getDay6()).isEqualTo(6);
        assertThat(shiftRoster.getDay7()).isEqualTo(7);
        assertThat(shiftRoster.getDay8()).isEqualTo(8);
        assertThat(shiftRoster.getDay9()).isEqualTo(9);
        assertThat(shiftRoster.getDay10()).isEqualTo(10);
        assertThat(shiftRoster.getDay11()).isEqualTo(11);
        assertThat(shiftRoster.getDay12()).isEqualTo(12);
        assertThat(shiftRoster.getDay13()).isEqualTo(13);
        assertThat(shiftRoster.getDay14()).isEqualTo(14);
        assertThat(shiftRoster.getDay15()).isEqualTo(15);
        assertThat(shiftRoster.getDay16()).isEqualTo(16);
        assertThat(shiftRoster.getDay17()).isEqualTo(17);
        assertThat(shiftRoster.getDay18()).isEqualTo(18);
        assertThat(shiftRoster.getDay19()).isEqualTo(19);
        assertThat(shiftRoster.getDay20()).isEqualTo(20);
        assertThat(shiftRoster.getDay21()).isEqualTo(21);
        assertThat(shiftRoster.getDay22()).isEqualTo(22);
        assertThat(shiftRoster.getDay23()).isEqualTo(23);
        assertThat(shiftRoster.getDay24()).isEqualTo(24);
        assertThat(shiftRoster.getDay25()).isEqualTo(25);
        assertThat(shiftRoster.getDay26()).isEqualTo(26);
        assertThat(shiftRoster.getDay27()).isEqualTo(27);
        assertThat(shiftRoster.getDay28()).isEqualTo(28);
        assertThat(shiftRoster.getDay29()).isEqualTo(29);
        assertThat(shiftRoster.getDay30()).isEqualTo(30);
        assertThat(shiftRoster.getDay31()).isEqualTo(31);
        assertThat(shiftRoster.getMonth()).isEqualTo(7);
        assertThat(shiftRoster.getYear()).isEqualTo(2024);
        assertThat(shiftRoster.getCreatedDt()).isEqualTo(Timestamp.valueOf("2024-07-01 00:00:00"));
        assertThat(shiftRoster.getCreatedBy()).isEqualTo("admin");
        assertThat(shiftRoster.getLastUpdatedDt()).isEqualTo(Timestamp.valueOf("2024-07-02 00:00:00"));
        assertThat(shiftRoster.getLastUpdatedBy()).isEqualTo("admin");
    }

}
