package com.itime.compoff.enumeration;

public enum EnumCompOffUsageStatus {

    AVAILABLE("AVAILABLE"), USED("USED"), EXPIRED("EXPIRED"), REQUESTED("REQUESTED"),INVALID("INVALID");

    String compOffUsageStatus;

    EnumCompOffUsageStatus(String compOffUsageStatus) {
        this.compOffUsageStatus = compOffUsageStatus;
    }

    public String getCompOffUsageStatus() {
        return compOffUsageStatus;
    }

    public static EnumCompOffUsageStatus compOffUsageStatusValue(String value) {
        for (EnumCompOffUsageStatus e : values()) {
            if (e.compOffUsageStatus.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
