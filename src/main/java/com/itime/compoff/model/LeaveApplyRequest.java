package com.itime.compoff.model;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class LeaveApplyRequest {

    private long employeeId;

    private Timestamp startDt;

    private Timestamp endDt;

    private Double noOfDays;

    private String reason;



}
