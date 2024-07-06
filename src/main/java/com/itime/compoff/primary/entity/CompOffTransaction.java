package com.itime.compoff.primary.entity;


import com.itime.compoff.enumeration.EnumCompOffPeriod;
import com.itime.compoff.enumeration.EnumCompOffTransactionStatus;
import com.itime.compoff.enumeration.EnumCompOffUsageStatus;
import com.itime.compoff.enumeration.EnumStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Time;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@Table(name = "lms_comp_off_transaction")
public class CompOffTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "employee_id")
    private long employeeId;

    @Column(name = "requested_date", nullable = false)
    private Timestamp requestedDate;

    @Column(name = "expiry_date", nullable = false)
    private Timestamp expiryDate;

    @Column(name = "punch_in_time", nullable = false)
    private Time punchInTime;

    @Column(name = "punch_out_time", nullable = false)
    private Time punchOutTime;

    @Column(name = "work_hours", nullable = false)
    private Double workHours;

    @Column(name = "reason")
    private String reason;

    @Column(name = "approver_id", nullable = false)
    private long approverId;

    @Column(name = "approver_remarks")
    private String approverRemarks;

    @Column(name = "cancellation_reason")
    private String cancellationReason;

    @Enumerated(EnumType.STRING)
    @Column(name = "requested_for", nullable = false)
    private EnumCompOffPeriod requestedFor;

    @Enumerated(EnumType.STRING)
    @Column(name = "approved_for")
    private EnumCompOffPeriod approvedFor;

    @Enumerated(EnumType.STRING)
    @Column(name = "transaction_status", nullable = false)
    private EnumCompOffTransactionStatus transactionStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "compoff_usage_status", nullable = false)
    private EnumCompOffUsageStatus compOffUsageStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leave_transaction_id", referencedColumnName = "id")
    private LeaveTransaction leaveTransaction;

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

}