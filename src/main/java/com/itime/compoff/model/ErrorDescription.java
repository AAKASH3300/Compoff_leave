package com.itime.compoff.model;

public class ErrorDescription {
    private String message;
    private String code;

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setCode(String code) {
        this.code = code;
    }
}