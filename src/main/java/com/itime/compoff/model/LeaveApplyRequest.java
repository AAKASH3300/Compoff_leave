package com.itime.compoff.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class LeaveApplyRequest {

    @NotBlank(message = "Employee ID is mandatory")
    private String employeeId;

    @NotNull(message = "Start date is mandatory")
    private Timestamp startDt;

    @NotNull(message = "End date is mandatory")
    private Timestamp endDt;

    @NotNull(message = "Number of days is mandatory")
    @Positive(message = "Number of days must be positive")
    private Double noOfDays;

    @NotBlank(message = "Reason is mandatory")
    @Size(max = 255, message = "Reason must be less than 255 characters")
    private String reason;


}
