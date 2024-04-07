package com.healthcare.system.entities;


public class Report {
    private String advice;
    private Status condition;

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
}
