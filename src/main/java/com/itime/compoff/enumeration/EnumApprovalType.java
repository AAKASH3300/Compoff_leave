package com.itime.compoff.enumeration;

public enum EnumApprovalType {
	
	REGULARIZATION("REGULARIZATION"), LEAVE("LEAVE"), PERMISSION("PERMISSION"), SHIFTCHANGE("SHIFTCHANGE"),COMPOFF("COMPOFF");
	
	String approvalValue;
	
	EnumApprovalType(String approvalValue) {
	        this.approvalValue = approvalValue;
	    }

	    public String getApprovalValue() {
	        return approvalValue;
	    }

	    public static EnumApprovalType approvalStatusValue(String value) {
	        for (EnumApprovalType e : values()) {
	            if (e.approvalValue.equals(value)) {
	                return e;
	            }
	        }
	        return null;
	    }

}
