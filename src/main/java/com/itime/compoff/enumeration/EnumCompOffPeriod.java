package com.itime.compoff.enumeration;

import lombok.Getter;

@Getter
public enum EnumCompOffPeriod {

    HALF_DAY("HALF_DAY",  0.5),
    FULL_DAY("FULL_DAY",  1.0);

    String value;


    double days;

    EnumCompOffPeriod(String value, double days) {
        this.value = value;
        this.days = days;
    }

    public static EnumCompOffPeriod valuesOf(String value) {
        for (EnumCompOffPeriod e : values()) {
            if (e.value.equalsIgnoreCase(value)) {
                return e;
            }
        }
        return null;
    }
}
