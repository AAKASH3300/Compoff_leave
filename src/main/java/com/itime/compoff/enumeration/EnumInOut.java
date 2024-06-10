package com.itime.compoff.enumeration;

public enum EnumInOut {


    IN("IN", "0"), OUT("OUT", "1");

    String punchType;
    String punchTypeInt;

    private EnumInOut(String punchType, String punchTypeInt) {
        this.punchType = punchType;
        this.punchTypeInt = punchTypeInt;

    }

    public String getPunchType() {
        return punchType;
    }

    public String getPunchTypeInt() {
        return punchTypeInt;
    }

    public static EnumInOut valueOfPunchType(String value) {
        for (EnumInOut e : values()) {
            if (e.punchType.equals(value)) {
                return e;
            }
        }
        return null;
    }
    public static EnumInOut valuesOf(String value) {
        for (EnumInOut e : values()) {
            if (e.punchTypeInt.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
