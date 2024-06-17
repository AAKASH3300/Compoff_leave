package com.itime.compoff.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveResponse {


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
    private String status;
    private String appliedOn;
    private String createdByEmpId;





}
