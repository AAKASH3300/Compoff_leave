package com.itime.compoff.exception;

public class ErrorMessages {

    public static final String RECORD_NOT_FOUND = "Record Not Found";
    public static final String EMPLOYEE_ID_NOT_FOUND = "Employee not found";
    public static final String APPRAISER_ID_NOT_FOUND = "Appraiser not found";
    public static final String MISSING_MANDATORY_START_DATE = "startDate";
    public static final String MISSING_MANDATORY_END_DATE = "endDate";
    public static final String INVALID_COMPOFF_REQUEST_FOR = "Invalid value on request for";
    public static final String INVALID_COMPOFF_REQUEST = "You cannot request for comp-off on shift assigned date.";
    public static final String WORK_HOUR_NOT_ENOUGH_COMPOFF = "Request denied, because work hours is less than %d hrs.";
    public static final String WORK_HOUR_NOT_ENOUGH_FULLDAY_COMPOFF = "Work hours should not less than %d for full day request.";
    public static final String INVALID_COMPOFF_REQUEST_NO_WORKHOUR = "Request denied, because you haven't punch in or out on requested date.";
    public static final String INVALID_COMPOFF_REQUEST_DATE = "Request denied, you cannot raise request on future date.";
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String ALREADY_REQUEST_RAISED = "Already request raised for this date";
    public static final String UNABLE_TO_CREDIT_COMPOFF = "Unable to credit comp-off leave because no eligible leave type for %s.";
    public static final String INVALID_COMPOFF_TIME_RANGE = "CompOff Type is not available for past days";
    static final String ALREADY_COMPOFF_UPDATED = "Cannot update the %s item";
    final String INVALID_REGULARIZATION_WORK_HOURS = "You must have 8.30 minimum work hours";
    protected ErrorMessages() {
    }

}
