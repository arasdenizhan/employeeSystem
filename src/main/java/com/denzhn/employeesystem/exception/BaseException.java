package com.denzhn.employeesystem.exception;

public abstract class BaseException extends Exception{
    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }
}
