package com.healthcare.system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DoctorDTO implements ObjectDTO {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private int healthProviderId;
    private String sessionId;
}
