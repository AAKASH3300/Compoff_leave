package com.itime.compoff.enumeration;

public enum EnumCompOffPeriod {

    HALF_DAY("HALF_DAY", "Half Day", 0.5),
    FULL_DAY("FULL_DAY", "Full Day", 1.0);

    String value;
    String emailValue;

    double days;

    EnumCompOffPeriod(String value, String emailValue, double days) {
        this.value = value;
        this.emailValue = emailValue;
        this.days = days;
    }

    public String getValue() {
        return value;
    }

    public String getEmailValue() {
        return emailValue;
    }

    public double getDays() {
        return days;
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
