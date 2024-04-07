package com.healthcare.system.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Prescription {
    private List<Medicine> medicines;

    public Prescription() {
        this.medicines = new ArrayList<>();
    }

}
