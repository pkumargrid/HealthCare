package com.healthcare.system.exceptions;

public class AlreadyLoggedOutException extends Exception {

    public AlreadyLoggedOutException(String msg) {
        super(msg);
    }
}
