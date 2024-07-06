package com.itime.compoff.primary.entity;

import com.itime.compoff.enumeration.EnumStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "eib_holidays")
public class Holiday {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "date", nullable = false)
    private Timestamp date;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnumStatus status;

    @Column(name = "created_dt", nullable = false)
    private java.sql.Timestamp createdDt;

    @Column(name = "created_by", nullable = false)
    private String createdBy;

    @Column(name = "last_updated_dt", nullable = false)
    private java.sql.Timestamp lastUpdatedDt;

    @Column(name = "last_updated_by", nullable = false)
    private String lastUpdatedBy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reference_holiday_id", referencedColumnName = "id")
    private Holiday referenceHolidayId;

    @Column(name = "description")
    private String description;


}
