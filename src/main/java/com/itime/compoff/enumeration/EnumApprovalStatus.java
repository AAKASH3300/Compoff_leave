package com.itime.compoff.enumeration;

public enum EnumApprovalStatus {

    PENDING("PENDING"), APPROVED("APPROVED"), REJECTED("REJECTED"), CANCELLED("CANCELLED");

    String approvalStatus;

    EnumApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
    }

    public String getApprovalStatus() {
        return approvalStatus;
    }

    public static EnumApprovalStatus approvalStatusValue(String value) {
        for (EnumApprovalStatus e : values()) {
            if (e.approvalStatus.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
