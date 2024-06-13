package com.itime.compoff.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LeaveResponse {


    private long id;
    private String tenantId;
    private String employeeId;
    private String leaveTypeId;
    private String startDt;
    private String endDt;
    private String noOfDays;
    private String leaveStatus;
    private String reason;
    private String leaveForStartDt;
    private String leaveForEndDt;
    private String reportingManager;
    private String approverRemarks;
    private String status;
    private String appliedOn;
    private String createdBy;
    private String lastUpdatedDt;
    private String lastUpdatedBy;
    private String leaveTypeName;

    private String createdByEmpId;

    private String employeeName;



}
