package com.healthcare.system.entities;

import java.util.ArrayList;
import java.util.List;

public class Prescription {
    private List<Medicine> medicines;

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public Prescription() {
        this.medicines = new ArrayList<>();
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
