package com.itime.compoff.exception;

public class DataNotFoundException extends CommonException {

    private static final long serialVersionUID = 1L;

    public DataNotFoundException(String message, Integer code) {
        super(message, code);
    }
}

