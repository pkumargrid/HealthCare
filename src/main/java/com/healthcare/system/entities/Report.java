package com.healthcare.system.entities;


public class Report {
    private int id;
    private String advice;
    private Status condition;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private Status status;

    public String getAdvice() {
        return advice;
    }

    public void setAdvice(String advice) {
        this.advice = advice;
    }

    public Status getCondition() {
        return condition;
    }

    public void setCondition(Status condition) {
        this.condition = condition;
    }
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
