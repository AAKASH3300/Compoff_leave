package com.itime.compoff.enumeration;

import lombok.Getter;

@Getter
public enum EnumLeaveStatus {


	PENDING("PENDING"), APPROVED("APPROVED"), REJECTED("REJECTED"), CANCELLED("CANCELLED"), CANCELLATION_PENDING("CANCELLATION_PENDING");;

	String leaveStatus;
	
	EnumLeaveStatus(String leaveStatus) {
		this.leaveStatus = leaveStatus;
	}

    public static EnumLeaveStatus leaveStatusValue(String value) {
		for (EnumLeaveStatus e : values()) {
			if (e.leaveStatus.equals(value)) {
				return e;
			}
		}
		return null;
}
}
