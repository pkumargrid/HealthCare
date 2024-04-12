package com.healthcare.system.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Reason {

    private int id;

    private int type;

    private String tableName;

    private String text;

    private Complaint complaint;

}
