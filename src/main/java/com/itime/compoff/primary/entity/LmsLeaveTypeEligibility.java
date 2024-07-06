package com.itime.compoff.primary.entity;

import com.itime.compoff.enumeration.EnumStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;

@Entity
@Table(name="lms_leave_type_eligibility")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LmsLeaveTypeEligibility {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "id")
        private long id;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "leave_type_id", referencedColumnName = "id", nullable = false)
        private LeaveType leaveType;

        @Column(name = "from_date", nullable = false)
        private Timestamp fromDate;

        @Column(name = "to_date", nullable = false)
        private Timestamp toDate;

        @Column(name = "leave_allowed", nullable = false)
        private Double leaveAllowed;

        @Column(name = "allowed_min", nullable = false)
        private Double allowedMin;

        @Column(name = "allowed_max", nullable = false)
        private Double allowedMax;

        @Column(name = "club_weekend_leaves", nullable = false)
        private int clubWeekEnd;

        @Column(name = "club_holidays_leaves", nullable = false)
        private int clubHoliday;

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
