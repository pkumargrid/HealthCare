package com.healthcare.system.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NurseDTO {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private int healthProviderId;
}
