package com.itime.compoff.enumeration;

public enum EnumStatus {

	ACTIVE("ACTIVE",1), INACTIVE("INACTIVE",0);

	String value;
	int binary;

	EnumStatus(String value, int binary) {
		this.value = value;
		this.binary = binary;
	}

	public String getValue() {
		return value;
	}
	
	public int getBinary() {
		return binary;
	}

	public static EnumStatus valueOfStatus(String value) {
		for (EnumStatus e : values()) {
			if (e.value.equalsIgnoreCase(value)) {
				return e;
			}
		}
		return null;
	}
}
