package com.itime.compoff.exception;

public class ErrorMessages {

    protected ErrorMessages() {
    }

    //persistence
    public static final String TENANT_NOT_FOUND = "No tenant found for the given id";
    public static final String RECORD_NOT_FOUND = "Record Not Found";
    public static final String SHIFT_NOT_FOUND = "Shift is not assigned for you. Please contact your appraiser.";
    public static final String EMPLOYEE_ID_NOT_FOUND = "Employee not found";
    public static final String LOCATION_NOT_FOUND = "location not found";
    public static final String APPRAISERI_ID_NOT_FOUND = "Appraiser not found";
    public static final String DESIGNATION_NOT_FOUND = "Designation Not Found";

    public static final String PROJECT_ID_NOT_FOUND = "Project is not found";
    public static final String WIDGET_ID_NOT_FOUND = "widget id not found";
    public static final String ORGANIZATION_UNIT_ID_NOT_FOUND = "Organization not found";
    public static final String EMPLOYEE_EMAIL_NOT_FOUND = "Email ID not found";
    public static final String LMS_GROUP_ID_NOT_FOUND = "Lms Group not found";
    public static final String EIB_GROUP_ID_NOT_FOUND = "Eib Group not found";
    public static final String APP_FEATURE_ID_NOT_FOUND = "Feature id not found";
    public static final String PERMISSION_INNER_FIELDS = "Time entered for permission does not match with the assigned shift.";
    public static final String INVALID_PERMISSION_DATE = "Permission cannot be applied for the selected date. Select a working day in the current pay cycle.";

    public static final String PERMISSION_TYPE_ID_NOT_FOUND = "Permission type not found";
    public static final String INVALID_FORMAT_EMPLOYEE_ID = "Employee ID not found. Please enter the correct employee ID of your team member.";
    public static final String INVALID_EMPLOYEE_ID = "Employee ID not found. Please enter the correct employee ID of your team member.";

    public static final String INVALID_FORMAT_EIB_ID = "Invalid id format";
    public static final String INVALID_EIB_ID = "Invalid eibGroup id size";

    public static final String MISSING_EIB_ID = "Missing eibGroup id";
    public static final String INVALID_FORMAT_FEATURE_ID = "Invalid featureId format";
    public static final String INVALID_ID_SIZE = "Invalid id size";

    public static final String DUPLICATE_MAPPING = "Duplicate mapping for lms group id";
    public static final String INVALID_FORMAT_CREATE_PERMISSION = "Invalid create permission format";
    public static final String INVALID_FORMAT_READ_PERMISSION = "Invalid read permission format";
    public static final String DUPLICATE_DATA = "Organization already exists";

    public static final String INVALID_FORMAT_UPDATE_PERMISSION = "Invalid update permission format";
    public static final String INVALID_FORMAT_DELETE_PERMISSION = "Invalid delete permission format";
    public static final String INVALID_DESIGNATION_ID = "Invalid designation id";
    public static final String INVALID_LMS_GROUP_ID_FORMAT = "Invalid lms group id format";
    public static final String MISSING_MANDATORY_ID = "Group id's";

    public static final String MISSING_MANDATORY_START_DATE = "startDate";
    public static final String MISSING_MANDATORY_END_DATE = "endDate";


    public static final String INVALID_LOCATION_ID = "Invalid location";
    public static final String INVALID_DEPARTMENT_ID = "Invalid department ";

    public static final String INVALID_DIVISION_ID = "Invalid division";
    public static final String INVALID_SUBDIVISION_ID = "Invalid sub division";

    public static final String INVALID_DEPARTMENT_DIVISION_SUBDIVISION_ID = "Invalid department, division and sub division id";
    public static final String INVALID_DEPARTMENT_SUBDIVISION_ID = "Invalid department,sub division id";
    public static final String INVALID_DIVISION_SUBDIVISION_ID = "Invalid division and sub division id";

    public static final String INVALID_GRADE_ID = "Invalid grade id";

    public static final String INVALID_ID = "Invalid id format";
    public static final String INVALID_STATUS = "Invalid status format";

    public static final String INVALID_EMPLOYEE_TYPE = "Invalid Employee type";
    public static final String INVALID_REGULARIZATION_DATE = "Regularization cannot be applied on the selected date.";
    public static final String INVALID_REGULARIZATION_WORK_HOURS = "You must have 8.30 minimum work hours";
    public static final String INVALID_GENDER = "Invalid gender";
    public static final String INVALID_MARITAL_STATUS = "Invalid marital status";
    public static final String INVALID_ELIGIBLE_TYPE = "Invalid eligible type %s";



    public static final String INVALID_FORMAT_ID = "Invalid id format";
    public static final String INVALID_SHIFT_ID = "Invalid shift";

    public static final String INVALID_DATE = "Invalid date : %s";


    public static final String DIVISION_NOT_FOUND = "Division not found";

    public static final String DEPARTMENT_NOT_FOUND = "Department not found";

    public static final String SUBDIVISION_NOT_FOUND = "SubDivision not found";

    public static final String MISSING_MANDATORY_LIST = "Mandatory list %s cannot be null or empty";

    public static final String HRA_ID_NOT_FOUND = "HRA is not found";


    // Error Key
    public static final String MSG_MANDATORY = "Mandatory field validation error : '{}' should not be empty";
    public static final String MSG_FIELD_SIZE = "Field size validation error : '{}'";
    public static final String MSG_FIELD_FORMAT = "Field format validation error : '{}'";
    public static final String MSG_VALIDATION = "Validation Error : '{}'";

    //Excel validation
    public static final String INVALID_HEADERS = "Invalid column header. Do not rename the columns in the downloaded template.";
    public static final String COLUMNS_NOT_IN_ORDER = "Columns are not in the correct order. Do not reorder the columns in the downloaded template.";
    public static final String INVALID_TIME_FORMAT ="Invalid time format: %s. Use the time format mentioned in the downloaded template.";
    public static final String MISSING_MANDATORY = "Fields are missing in the following rows: %s";

    public static final String MISSING_MANDATORY_FIELD = "Missing mandatory columns. Do not delete any columns in the downloaded template.";
    public static final String ABSENT_SCHEDULE_VALIDATION = "If absent schedule is true, schedule time and and date must be present";
    public static final String ALLOWED_TIME_VALIDATION = "Either allowed in and out time, both should be present or empty";
    public static final String INTERNAL_ERROR = "Internal server error";
    public static final String INVALID_SHIFT_TYPE = "Invalid shift type %s";
    public static final String INVALID_TIME_RANGE = "Invalid time range";
    public static final String COULD_NOT_PROCESS = "Could not process the file. Check the file format and please try again.";
    public static final String INVALID_ROW_COUNT = "%d error(s) occurred";
    public static final String EXISTING_TIME_SLOT = "This timeslot has been already alloted to another shift";

    public static final String INVALID_STARTDATE_ENDDATE = "Start date(%s) should be greater than End date(%s)";
    public static final String INVALID_WEEKOFF = "Maximum two day off permit per week";
    public static final String INVALID_WEEKOFF_EMPTY = "Minimum one day off per week is required.";

    public static final String INVALID_SHIFT = "Invalid shift";
    public static final String INVALID_SHIFT_EXCEL = "Invalid shift : %s";

    public static final String INVALID_LEAVE_TYPE = "Invalid type";
    public static final String INVALID_ACCURAL_CONFIG = "Invalid accural configuration";
    public static final String INVALID_VALUE = "Invalid value : %s";
    public static final String INVALID_EFFECTIVE_CONFIG = "Invalid effective after configuration";
    public static final String INVALID_MAX_LEAVES = "Max availed days should be less than or equal to entitlement days";

    public static final String INVALID_PERMISSION_HOURS = "You do not have sufficient permission balance.";

    public static final String INVALID_PERMISSION_REQUEST = "Please check your available permission balance";

    public static final String INVALID_PERMISSION_TYPE = "Invalid permission type";

    public static final String INVALID_DATA = "Invalid %s (%s)";
    public static final String LEAVE_TYPE_NOT_FOUND = "Leave type not found";
    public static final String INVALID_WORKFLOW_ROLE = "Invalid workflow role %s";
    public static final String DUPLICATE_ROLE_SELECTED = "Duplicate role selected in approval levels";

    public static final String PERMISSION_TYPE_NOT_FOUND =  "Permission type not found";
    public static final String INVALID_PERMISSION_WEEKOFF =  "You cannot apply permission for week off day";
    public static final String INVALID_PERMISSION_LEAVE =  "Permission cannot be applied on the selected date as a Leave request has been raised for the same date.";

    public static final String JSON_ERROR = "Reading json error";
    public static final String START_DATE = "Start date must be working day";
    public static final String END_DATE = "End date must be working day";

    //Regularization
    public static final String MISSING_PUNCHIN = "Missing punch-in time";
    public static final String MISSING_PUNCHOUT = "Missing punch-out time";
    public static final String INVALID_CREDENTIALS = "Invalid username or password";
    public static final String INVALID_LEAVESUB_TYPE = "Invalid leave subtype";

    public static final String INVALID_MODULE = "Invalid Module";
    public static final String INVALID_HOLIDAY = "Invalid Holiday";
    public static final String PAID_LEAVE_UNAVAILABLE = "Probation period employees are not eligible to apply paid leaves.";
    public static final String COULD_NOT_CONNECT_TO_AUTHORIZATION_SERVER = "Could not connect to the authorization server. Please try again.";
    public static final String UNAUTHORIZED_REQUEST = "Un-authorized request.";

    public static final String FUNCTIONAL_CATEGORY_NOT_FOUND = "Functional category not found.";
    public static final String ATTENDANCE_CONFIG_NOT_FOUND = "Attendance configuration not found";

    public static final String ABSENT_REPLACEMENT_HOLIDAY = "If replacement holiday is true, replacement name and and date must be present";
    public static final String INVALID_COMPOFF_REQUEST_FOR = "Invalid value on request for";

    public static final String INVALID_COMPOFF_REQUEST = "You cannot request for comp-off on shift assigned date.";
    public static final String WORK_HOUR_NOT_ENOUGH_COMPOFF = "Request denied, because work hours is less than %d hrs.";
    public static final String WORK_HOUR_NOT_ENOUGH_FULLDAY_COMPOFF = "Work hours should not less than %d for full day request.";
    public static final String INVALID_COMPOFF_REQUEST_NO_WORKHOUR = "Request denied, because you haven't punch in or out on requested date.";
    public static final String INVALID_COMPOFF_REQUEST_DATE = "Request denied, you cannot raise request on future date.";
    public static final String INVALID_REQUEST = "Invalid request";
    public static final String ALREADY_REQUEST_RAISED = "Already request raised for this date";
    public static final String INVALID_SIX_DP_REQUEST = "Request denied, based on selected work week record you are not eligible to apply 6th day pay.";
    public static final String NOT_ELIGIBLE_GRADE_SIX_DP = "Request denied, 6th day pay not available for your grade.";
    public static final String SYSTEM_WORKFLOW_NOT_FOUND = "workflow config id not found";
    public static final String UNABLE_TO_READ_VALUE = "Unable to read value from %s";
    public static final String ALREADY_COMPOFF_UPDATED = "Cannot update the %s item";
    public static final String UNABLE_TO_CREDIT_COMPOFF = "Unable to credit comp-off leave because no eligible leave type for %s.";
    public static final String LOP_NOT_AVAILABLE = "You don't have any LOP on this date";
    public static final String INVALID_COMPOFF_TIME_RANGE = "CompOff Type is not available for past days";
    public static final String INVALID_SIX_DAY_PAY = "Sixth day shift revoked for this date.";

    public static final String INVALID_WEEKOFF_VALIDATION = "Minimum one day off and Maximum two day off per week is required.";
    public static final String SHIFT_OVERLAP_VALIDATION =
            "Shift Timings are overlapped on %s! there must be minimum 8 hour gap betweens each shift. Kindly select another Shift";
}
