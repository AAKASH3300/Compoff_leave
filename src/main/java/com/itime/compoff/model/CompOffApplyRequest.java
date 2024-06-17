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
public class CompOffApplyRequest {

    @NotNull(message = "Employee ID cannot be null")
    private Long employeeId;

    @NotNull(message = "Requested date cannot be null")
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}",
            message = "Requested date must be in the format YYYY-MM-DD HH:mm:ss")
    private String requestedDate;

    @Pattern(regexp = "([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)",
            message = "Punch in time must be in the format HH:mm:ss")
    private String punchIn;

    @Pattern(regexp = "([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)",
            message = "Punch out time must be in the format HH:mm:ss")
    private String punchOut;

    @Pattern(regexp = "\\d+(:[0-5]\\d)?",
            message = "Work hours must be a valid number, optionally in the format H:mm or HH:mm")
    private String workHours;

    @NotBlank(message = "Requested for cannot be blank")
    @Size(max = 50, message = "Requested for cannot exceed 50 characters")
    private String requestedFor;

    @Size(max = 200, message = "Reason cannot exceed 200 characters")
    private String reason;
}
