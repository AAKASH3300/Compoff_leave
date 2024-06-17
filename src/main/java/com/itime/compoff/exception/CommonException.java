package com.itime.compoff.exception;

import java.io.Serial;

public class CommonException extends Exception{

    @Serial
    private static final long serialVersionUID = 1L;

    private final String message;

    public CommonException(String message) {
        this.message = message;

    }

    @Override
    public String getMessage() {
        return message;
    }

}
