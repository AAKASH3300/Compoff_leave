package com.itime.compoff.primary.entity;


import com.itime.compoff.enumeration.EnumInOut;
import com.itime.compoff.enumeration.EnumStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "eib_web_punch_transaction")
@Getter
@Setter
public class WebPunchTransaction implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "employee_id",nullable = false)
    private long employeeId;

    @Column(name = "punch_log", nullable = false)
    private Timestamp punchLog;

    @Enumerated(EnumType.STRING)
    @Column(name = "punch_type", nullable = false)
    private EnumInOut punchInOut;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EnumStatus status;

}
