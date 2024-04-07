package com.healthcare.system.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Complaint {

    private int id;

    private String text;

    private Patient patient;

}
