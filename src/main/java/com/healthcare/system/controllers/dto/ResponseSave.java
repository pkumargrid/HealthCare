package com.healthcare.system.controllers.dto;

public class ResponseSave {
    public String successful;

    public String getSuccessful() {
        return successful;
    }

    public ResponseSave(String successful) {
        this.successful = successful;
    }

    public void setSuccessful(String successful) {
        this.successful = successful;
    }
}
