package com.itime.compoff.enumeration;

import lombok.Getter;

@Getter
public enum EnumApprovalStatus {

    PENDING("PENDING"), APPROVED("APPROVED"), REJECTED("REJECTED"), CANCELLED("CANCELLED");

    final String approvalStatus;

    EnumApprovalStatus(String approvalStatus) {
        this.approvalStatus = approvalStatus;
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
