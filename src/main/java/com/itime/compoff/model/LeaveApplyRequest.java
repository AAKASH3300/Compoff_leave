package com.itime.compoff.model;

public class LeaveApplyRequest {

    private String employeeId;
    private String leaveTypeId;
    private String startDt;
    private String endDt;
    private String noOfDays;
    private String reason;
    private String leaveForStartDt;
    private String leaveForEndDt;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getLeaveTypeId() {
        return leaveTypeId;
    }

    public void setLeaveTypeId(String leaveTypeId) {
        this.leaveTypeId = leaveTypeId;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getEndDt() {
        return endDt;
    }

    public void setEndDt(String endDt) {
        this.endDt = endDt;
    }

    public String getNoOfDays() {
        return noOfDays;
    }

    public void setNoOfDays(String noOfDays) {
        this.noOfDays = noOfDays;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getLeaveForStartDt() {
        return leaveForStartDt;
    }

    public void setLeaveForStartDt(String leaveForStartDt) {
        this.leaveForStartDt = leaveForStartDt;
    }

    public String getLeaveForEndDt() {
        return leaveForEndDt;
    }

    public void setLeaveForEndDt(String leaveForEndDt) {
        this.leaveForEndDt = leaveForEndDt;
    }
}
