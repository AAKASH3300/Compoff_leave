package com.itime.compoff.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CompOffApplyRequest {
    private Long employeeId;
    private String requestedDate;
    private String punchIn;
    private String punchOut;
    private String workHours;
    private String requestedFor;
    private String reason;
}
