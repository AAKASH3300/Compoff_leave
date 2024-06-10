package com.itime.compoff.exception;

import java.io.Serial;

public class CommonException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;
    private final Integer code;

    public CommonException(String message, Integer code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
