package com.itime.compoff.enumeration;

public enum EnumLeavePeriod {

	HALF_DAY("HALF_DAY","Half day"),
	FULL_DAY("FULL_DAY","Full day");

	String period;
	String descripition;

	private EnumLeavePeriod(String period, String descripition) {
		this.period = period;
		this.descripition=descripition;
	}

	public String getPeriod() {
		return period;
	}

	public String getDescripition() {
		return descripition;
	}

	public static EnumLeavePeriod valueOfPeriod(String value) {
		for (EnumLeavePeriod e : values()) {
			if (e.period.equals(value)) {
				return e;
			}
		}
		return null;
	}
}
