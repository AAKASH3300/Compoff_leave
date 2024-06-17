package com.itime.compoff.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CompOffTransactionResponse {

    private long id;

    private String employeeId;

    private String empCode;

    private String approverId;

    private String requestedDt;

    private String requestType;

    private String workHours;

    private String requestedFor;

    private String approvedFor;

    private String reason;

    private String transactionStatus;

    private String createdBy;

    private String approverRemarks;

    private String punchInTime;

    private String punchOutTime;

    private String compOffUsageStatus;

}