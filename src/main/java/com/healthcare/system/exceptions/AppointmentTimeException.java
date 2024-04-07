package com.healthcare.system.exceptions;

public class AppointmentTimeException extends Exception{

    private int status;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public AppointmentTimeException(String timeSlotsNotAvailable) {
        super(timeSlotsNotAvailable);
    }
}
