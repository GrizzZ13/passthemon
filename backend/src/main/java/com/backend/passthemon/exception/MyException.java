package com.backend.passthemon.exception;

public class MyException extends Exception{
    public MyException() {
        super();
    }

    public MyException(String message) {
        super(message);
    }

    @Override
    public String getMessage() {
        return super.getMessage();
    }
}
