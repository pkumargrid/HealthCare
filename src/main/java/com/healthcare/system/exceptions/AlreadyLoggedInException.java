package com.healthcare.system.exceptions;

public class AlreadyLoggedInException extends Exception{
    public AlreadyLoggedInException(String msg) {
        super(msg);
    }
}
