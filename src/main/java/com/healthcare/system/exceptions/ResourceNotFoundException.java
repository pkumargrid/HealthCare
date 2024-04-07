package com.healthcare.system.exceptions;

public class ResourceNotFoundException extends Exception {

    public ResourceNotFoundException(String msg) {
        super("Resource: " + msg + "  found! ");
    }

}
