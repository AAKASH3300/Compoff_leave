package com.itime.compoff.model;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LeaveApplyRequest {

    @NotBlank(message = "Employee ID cannot be blank")
    private String employeeId;

    @NotBlank(message = "Leave Type ID cannot be blank")
    private String leaveTypeId;

    @NotNull(message = "Start date cannot be null")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
            message = "Start date must be in the format YYYY-MM-DD HH:mm:ss")
    private String startDt;

    @NotNull(message = "End date cannot be null")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
             message = "End date must be in the format YYYY-MM-DD HH:mm:ss")
    private String endDt;

    @NotNull(message = "Number of days cannot be null")
    @Max(value = 10, message = "Number of days must not exceed 10")
    private String noOfDays;

    @NotBlank(message = "Reason cannot be blank")
    @Size(max = 500, message = "Reason cannot be longer than 500 characters")
    private String reason;

    @NotNull(message = "Leave for start date cannot be null")
    private String leaveForStartDt;

    @NotNull(message = "Leave for end date cannot be null")
    private String leaveForEndDt;


}
