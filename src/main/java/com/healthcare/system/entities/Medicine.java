package com.healthcare.system.entities;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Medicine {
    private String name;
    private int dose;
    List<DayTime> times;

    public Medicine() {
        this.times = new ArrayList<>();
    }

}
