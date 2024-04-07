package com.healthcare.system.exceptions;

public class WrongCredentials extends Exception{
    public WrongCredentials(String msg) {
        super(msg);
    }
}
