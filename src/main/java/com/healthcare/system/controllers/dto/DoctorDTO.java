package com.healthcare.system.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDTO {
    private String name;
    private String password;
    private String email;
    private int healthProviderId;

}
