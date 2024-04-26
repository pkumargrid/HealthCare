package com.healthcare.system.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @OneToMany
    @JoinColumn(name = "prescription_id")
    private List<Medicine> medicines;

    public Prescription() {
        this.medicines = new ArrayList<>();
    }

}
