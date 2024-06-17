package com.itime.compoff.primary.entity;


import com.itime.compoff.enumeration.EnumLeavePeriod;
import com.itime.compoff.enumeration.EnumLeaveStatus;
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
@Table(name = "lms_leave_transaction")
public class LeaveTransaction implements Serializable{


	@Serial
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private long id;

	@Column(name = "employee_id", nullable = false)
	private long employeeId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "leave_type_id ", referencedColumnName = "Id", nullable = false)
	private LeaveType leaveTypeId;

	@Column(name = "start_dt", nullable = false)
	private Timestamp startDt;

	@Column(name = "end_dt", nullable = false)
	private Timestamp endDt;

	@Column(name = "no_of_days", nullable = false)
	private Double noOfDays;

	@Enumerated(EnumType.STRING)
	@Column(name = "leave_status", nullable = false)
	private EnumLeaveStatus leaveStatus;

	@Column(name = "reason")
	private String reason;

	@Enumerated(EnumType.STRING)
	@Column(name = "leave_for_start_dt", nullable = false)
	private EnumLeavePeriod leaveForStartDt;

	@Enumerated(EnumType.STRING)
	@Column(name = "leave_for_end_dt", nullable = false)
	private EnumLeavePeriod leaveForEndDt;
	
	@Column(name = "cancellation_reason")
	private String cancellationReason;

	@Column(name = "reporting_manager",nullable = false)
	private long reportingManager;

	@Column(name = "approver_remarks")
	private String approverRemarks;

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
