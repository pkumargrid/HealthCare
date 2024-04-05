package com.healthcare.system.exceptions;

public class ReasonTypeException extends Exception {

    private int status;

    public ReasonTypeException(int status, String msg) {
        super(msg);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
