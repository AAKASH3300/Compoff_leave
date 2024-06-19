package com.itime.compoff.exception;


import java.io.Serial;

public class ValidationException extends CommonException{

	@Serial
	private static final long serialVersionUID = -3949136580323575600L;

	public ValidationException(String message) {
		super(message);
	}
}
