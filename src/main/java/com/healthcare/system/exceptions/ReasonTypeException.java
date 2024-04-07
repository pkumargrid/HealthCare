package com.healthcare.system.exceptions;

public class ReasonTypeException extends Exception {

    public ReasonTypeException(String msg) {
        super(msg);
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
