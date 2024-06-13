package com.itime.compoff.model;

public class CompOffSummaryResponse {

    private long approvedRequest;
    private long pendingRequest;
    private long rejectedRequest;
    private double availableCompOff;

    public long getApprovedRequest() {
        return approvedRequest;
    }

    public void setApprovedRequest(long approvedRequest) {
        this.approvedRequest = approvedRequest;
    }

    public long getPendingRequest() {
        return pendingRequest;
    }

    public void setPendingRequest(long pendingRequest) {
        this.pendingRequest = pendingRequest;
    }

    public long getRejectedRequest() {
        return rejectedRequest;
    }

    public void setRejectedRequest(long rejectedRequest) {
        this.rejectedRequest = rejectedRequest;
    }

    public double getAvailableCompOff() {
        return availableCompOff;
    }

    public void setAvailableCompOff(double availableCompOff) {
        this.availableCompOff = availableCompOff;
    }
}