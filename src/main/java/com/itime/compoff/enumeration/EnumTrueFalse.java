package com.itime.compoff.enumeration;

public enum EnumTrueFalse {

    TRUE(1,"Yes","TRUE"), FALSE(0,"No","FALSE");

    int binary;
    String value;
    String status;

     EnumTrueFalse(int binary, String yesOrNoValue, String trueOrFalse) {
        this.binary = binary;
        this.value = yesOrNoValue;
        this.status = trueOrFalse;
    }

    public int getBinaryValue(){
        return binary;
    }

    public String getValue() {
        return value;
    }

	public String getStatus() {
		return status;
	}
}
