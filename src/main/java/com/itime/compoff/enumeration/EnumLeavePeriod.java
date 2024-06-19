package com.itime.compoff.enumeration;

import lombok.Getter;

@Getter
public enum EnumLeavePeriod {

	HALF_DAY("HALF_DAY","Half day"),
	FULL_DAY("FULL_DAY","Full day");

	String period;
	String descripition;

	private EnumLeavePeriod(String period, String descripition) {
		this.period = period;
		this.descripition=descripition;
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
