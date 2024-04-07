package com.healthcare.system.entities;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Report {
    private int id;
    private String advice;
    private Status condition;

    private Status status;

}
