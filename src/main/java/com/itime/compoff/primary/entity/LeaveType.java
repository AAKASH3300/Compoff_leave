package com.itime.compoff.primary.entity;


import com.itime.compoff.enumeration.EnumStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "eib_leave_type")
public class LeaveType implements Serializable {


    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "leaves_allowed")
    private Double leavesAllowed;

    @Enumerated(EnumType.STRING)
    @Column(name = "leave_type_status", nullable = false)
    private EnumStatus leaveTypeStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnumStatus status;

    @Column(name = "created_dt", nullable = false)
    private Timestamp createdDt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "last_updated_dt", nullable = false)
    private Timestamp lastUpdatedDt;

    @Column(name = "last_updated_by", nullable = false)
    private String lastUpdatedBy;

    @Column(name = "club_weekend_leaves", nullable = false)
    private int clubWeekEnd;

    @Column(name = "club_holidays_leaves", nullable = false)
    private int clubHoliday;

}