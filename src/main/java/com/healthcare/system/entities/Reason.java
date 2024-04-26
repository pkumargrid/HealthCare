package com.healthcare.system.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Reason {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private Object type;

    private String text;

    private Complaint complaint;

}
