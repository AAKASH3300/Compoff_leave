package com.itime.compoff.exception;

public class ErrorMessages {

    public static final String RECORD_NOT_FOUND = "Record Not Found";
    public static final String INVALID_COMPOFF_REQUEST_FOR = "Invalid value on request for";
    public static final String WORK_HOUR_NOT_ENOUGH_COMPOFF = "Request denied, because work hours is less than %d hrs.";
    public static final String WORK_HOUR_NOT_ENOUGH_FULL_DAY_COMPOFF = "Work hours should not less than %d for full day request.";
    public static final String ALREADY_REQUEST_RAISED = "Already request raised for this date";
    public static final String INVALID_COMPOFF_REQUEST_DATE = "Request Denied,You cannot request comp off on future date.";
    public static final String INVALID_COMPOFF_TIME_RANGE = "Invalid Compoff time range leave date is in the past";
    public static final String UNABLE_TO_CREDIT_COMPOFF = "Unable to credit compoff : Leave Type not available";

    protected ErrorMessages() {
    }

}
