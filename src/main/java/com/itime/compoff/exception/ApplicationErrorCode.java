package com.itime.compoff.exception;

import lombok.Getter;

@Getter
public enum ApplicationErrorCode {

    INVALID_COMPOFF_STATUS(new ApplicationError("Invalid compOff status : %s")),

    ALREADY_COMP_OFF_UPDATED(new ApplicationError("The compOff was already %s")),

    INVALID_REQUEST(new ApplicationError("Invalid Leave status : %s"));

    private final ApplicationError error;

    ApplicationErrorCode(ApplicationError error) {
        this.error = error;
    }

}
