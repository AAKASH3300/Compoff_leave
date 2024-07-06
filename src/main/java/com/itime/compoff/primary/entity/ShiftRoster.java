package com.itime.compoff.primary.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@Table(name = "eib_emp_shift_roster")
public class ShiftRoster  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "employee_id",nullable = false)
    private long employeeId;

    @Column(name = "day_1")
    private Integer day1;
    @Column(name = "day_2")
    private Integer day2;
    @Column(name = "day_3")
    private Integer day3;
    @Column(name = "day_4")
    private Integer day4;
    @Column(name = "day_5")
    private Integer day5;
    @Column(name = "day_6")
    private Integer day6;
    @Column(name = "day_7")
    private Integer day7;

    @Column(name = "day_8")
    private Integer day8;

    @Column(name = "day_9")
    private Integer day9;

    @Column(name = "day_10")
    private Integer day10;

    @Column(name = "day_11")
    private Integer day11;

    @Column(name = "day_12")
    private Integer day12;

    @Column(name = "day_13")
    private Integer day13;

    @Column(name = "day_14")
    private Integer day14;

    @Column(name = "day_15")
    private Integer day15;

    @Column(name = "day_16")
    private Integer day16;

    @Column(name = "day_17")
    private Integer day17;

    @Column(name = "day_18")
    private Integer day18;

    @Column(name = "day_19")
    private Integer day19;

    @Column(name = "day_20")
    private Integer day20;

    @Column(name = "day_21")
    private Integer day21;

    @Column(name = "day_22")
    private Integer day22;
    @Column(name = "day_23")
    private Integer day23;
    @Column(name = "day_24")
    private Integer day24;
    @Column(name = "day_25")
    private Integer day25;
    @Column(name = "day_26")
    private Integer day26;
    @Column(name = "day_27")
    private Integer day27;
    @Column(name = "day_28")
    private Integer day28;
    @Column(name = "day_29")
    private Integer day29;
    @Column(name = "day_30")
    private Integer day30;

    @Column(name = "day_31")
    private Integer day31;

    @Column(name = "month", nullable = false)
    private int month;

    @Column(name = "year", nullable = false)
    private int year;

    @Column(name = "created_dt", nullable = false)
    private Timestamp createdDt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "last_updated_dt", nullable = false)
    private Timestamp lastUpdatedDt;

    @Column(name = "last_updated_by", nullable = false)
    private String lastUpdatedBy;

}

