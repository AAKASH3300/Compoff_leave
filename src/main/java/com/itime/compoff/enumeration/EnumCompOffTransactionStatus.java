package com.itime.compoff.enumeration;

public enum EnumCompOffTransactionStatus {

    PENDING("PENDING"), APPROVED("APPROVED"), REJECTED("REJECTED"), CANCELLED("CANCELLED");

    String compOffStatus;

    EnumCompOffTransactionStatus(String compOffStatus) {
        this.compOffStatus = compOffStatus;
    }

    public String getCompOffStatus() {
        return compOffStatus;
    }

    public static EnumCompOffTransactionStatus compOffStatusValue(String value) {
        for (EnumCompOffTransactionStatus e : values()) {
            if (e.compOffStatus.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
