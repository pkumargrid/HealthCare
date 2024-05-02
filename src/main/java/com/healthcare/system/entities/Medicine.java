package com.healthcare.system.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@Entity
@Builder
@AllArgsConstructor
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    private int dose;

    @ElementCollection
    List<DayTime> times;

    public Medicine() {
        this.times = new ArrayList<>();
    }

}
