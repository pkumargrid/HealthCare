package com.healthcare.system.controllers.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HealthProviderDTO {

    private int id;

    private String name;

    private String password;

    private String email;

}
