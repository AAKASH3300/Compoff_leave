package com.itime.compoff.primary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "eib_leave_summary")
public class LeaveSummary implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "employee_id")
    private long employeeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_type_id ", referencedColumnName = "Id", nullable = false)
    private LeaveType leaveTypeId;

    @Column(name = "period_start_dt", nullable = false)
    private Timestamp periodStartDt;

    @Column(name = "period_end_dt", nullable = false)
    private Timestamp periodEndDt;

    @Column(name = "leaves_taken", nullable = false)
    private Double leavesTaken;

    @Column(name = "leaves_available", nullable = false)
    private Double leavesAvailable;

    @Column(name = "leave_open")
    private Double leaveOpen;

    @Column(name = "leave_credit")
    private Double leaveCredit;

}
