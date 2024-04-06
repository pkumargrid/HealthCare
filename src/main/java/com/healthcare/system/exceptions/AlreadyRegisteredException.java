package com.healthcare.system.exceptions;

public class AlreadyRegisteredException extends Exception {
    public AlreadyRegisteredException(String msg) {
        super(msg);
    }
}
