package com.itime.compoff.model;

public class CompOffTransactionResponse {

    private long id;
    private String tenantId;
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

    private String createdOn;

    private String lastUpdatedBy;

    private String lastUpdatedOn;

    private String employeeName;

    private String approverName;

    private String approverRemarks;

    private String punchInTime;

    private String punchOutTime;

    private String compOffUsageStatus;

    public CompOffTransactionResponse() {
    }

    public CompOffTransactionResponse(long id, String employeeId, String empCode, String approverId,
                                      String requestedDt, String requestType, String workHours, String requestedFor, String reason,
                                      String transactionStatus, String employeeName, String punchInTime, String punchOutTime) {
        this.id = id;
        this.employeeId = employeeId;
        this.empCode = empCode;
        this.approverId = approverId;
        this.requestedDt = requestedDt;
        this.requestType = requestType;
        this.workHours = workHours;
        this.requestedFor = requestedFor;
        this.reason = reason;
        this.transactionStatus = transactionStatus;
        this.employeeName = employeeName;
        this.punchInTime = punchInTime;
        this.punchOutTime = punchOutTime;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmpCode() {
        return empCode;
    }

    public void setEmpCode(String empCode) {
        this.empCode = empCode;
    }

    public String getApproverId() {
        return approverId;
    }

    public void setApproverId(String approverId) {
        this.approverId = approverId;
    }

    public String getRequestedDt() {
        return requestedDt;
    }

    public void setRequestedDt(String requestedDt) {
        this.requestedDt = requestedDt;
    }

    public String getRequestType() {
        return requestType;
    }

    public void setRequestType(String requestType) {
        this.requestType = requestType;
    }

    public String getWorkHours() {
        return workHours;
    }

    public void setWorkHours(String workHours) {
        this.workHours = workHours;
    }

    public String getRequestedFor() {
        return requestedFor;
    }

    public void setRequestedFor(String requestedFor) {
        this.requestedFor = requestedFor;
    }

    public String getApprovedFor() {
        return approvedFor;
    }

    public void setApprovedFor(String approvedFor) {
        this.approvedFor = approvedFor;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus) {
        this.transactionStatus = transactionStatus;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getLastUpdatedBy() {
        return lastUpdatedBy;
    }

    public void setLastUpdatedBy(String lastUpdatedBy) {
        this.lastUpdatedBy = lastUpdatedBy;
    }

    public String getLastUpdatedOn() {
        return lastUpdatedOn;
    }

    public void setLastUpdatedOn(String lastUpdatedOn) {
        this.lastUpdatedOn = lastUpdatedOn;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getApproverName() {
        return approverName;
    }

    public void setApproverName(String approverName) {
        this.approverName = approverName;
    }

    public String getApproverRemarks() {
        return approverRemarks;
    }

    public void setApproverRemarks(String approverRemarks) {
        this.approverRemarks = approverRemarks;
    }

    public String getPunchInTime() {
        return punchInTime;
    }

    public void setPunchInTime(String punchInTime) {
        this.punchInTime = punchInTime;
    }

    public String getPunchOutTime() {
        return punchOutTime;
    }

    public void setPunchOutTime(String punchOutTime) {
        this.punchOutTime = punchOutTime;
    }

    public String getCompOffUsageStatus() {
        return compOffUsageStatus;
    }

    public void setCompOffUsageStatus(String compOffUsageStatus) {
        this.compOffUsageStatus = compOffUsageStatus;
    }
}