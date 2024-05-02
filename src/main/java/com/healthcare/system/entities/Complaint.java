package com.healthcare.system.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String text;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name ="patient_id")
    @JsonBackReference
    private Patient patient;

    private int type;
    private String tableName;

}
