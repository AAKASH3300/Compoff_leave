package com.itime.compoff.advice;

import com.itime.compoff.exception.CommonException;
import com.itime.compoff.exception.DataNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;


import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleInvalidArgument(MethodArgumentNotValidException exception) {
        Map<String, String> errorMsg = new HashMap<>();
        exception.getBindingResult().getFieldErrors()
                .forEach(error -> errorMsg.put(error.getField(), error.getDefaultMessage()));
        return errorMsg;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(DataNotFoundException.class)
    public Map<String, String> notFoundException(DataNotFoundException error) {
        Map<String, String> errObj = new HashMap<>();
        errObj.put("ERROR", error.getMessage());
        return errObj;
    }


    @ResponseStatus(HttpStatus.FOUND)
    @ExceptionHandler(IllegalArgumentException.class)
    public Map<String, String> illegalArgumentException(IllegalArgumentException error) {
        Map<String, String> errObj = new HashMap<>();
        errObj.put("ERROR", error.getMessage());
        return errObj;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(CommonException.class)
    public Map<String, String> commonException(CommonException error) {
        Map<String, String> errObj = new HashMap<>();
        errObj.put("ERROR", error.getMessage());
        return errObj;
    }

}