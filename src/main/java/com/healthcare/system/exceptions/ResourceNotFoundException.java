package com.healthcare.system.exceptions;

public class ResourceNotFoundException extends Exception {
    private int status;

    public ResourceNotFoundException(String msg, int status) {
        super("Resource: " + msg + "  found! ");
        this.status = status;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

}
