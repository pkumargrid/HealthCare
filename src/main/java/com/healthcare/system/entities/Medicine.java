package com.healthcare.system.entities;

import java.util.ArrayList;
import java.util.List;

public class Medicine {
    private String name;
    private int dose;
    List<DayTime> times;

    public Medicine() {
        this.times = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDose() {
        return dose;
    }

    public void setDose(int dose) {
        this.dose = dose;
    }

    public List<DayTime> getTimes() {
        return times;
    }

    public void setTimes(List<DayTime> times) {
        this.times = times;
    }
}
