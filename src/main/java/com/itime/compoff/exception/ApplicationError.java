package com.itime.compoff.exception;

public class ApplicationError {

    private final String description;

    public ApplicationError(String description) {
        super();
        this.description = description;
    }

    public ValidationException reqValidationError(String data) {
        String desc = String.format(description, data);
        return new ValidationException(desc);
    }

    public ValidationException reqValidationError() {
        return new ValidationException(description);
    }

    public CommonException commonApplicationError() {
        return new CommonException(description);
    }

    public CommonException commonApplicationError(String data) {
        String desc = String.format(description, data);
        return new CommonException(desc);
    }
}
