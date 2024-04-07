package com.healthcare.system.controllers.dto;

import com.healthcare.system.entities.HealthProvider;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PatientDTO {
    private String name;
    private String password;
    private String email;
}
