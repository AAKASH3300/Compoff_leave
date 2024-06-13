package com.itime.compoff.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompOffApplyRequest {

    @NotBlank
    private Long employeeId;

    @NotBlank
    private String requestedDate;

    @NotBlank
    private String punchIn;

    @NotBlank
    private String punchOut;

    @NotBlank
    private String workHours;

    @NotBlank
    private String requestedFor;

    @NotBlank
    private String reason;
}
