package com.healthcare.system.controllers.dto;

import com.healthcare.system.entities.HealthProvider;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NurseDTO {
    private String name;
    private String password;
    private String email;
    private int healthProviderId;
}
