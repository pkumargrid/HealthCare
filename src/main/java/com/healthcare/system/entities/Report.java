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
public class Report {
<<<<<<< HEAD
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String advice;
    private Status condition;

=======

    private int id;
    private String advice;
    private Status status;
>>>>>>> 0d0b0e05f28cc077e7d42a1a22f542fe7df0398b

}
