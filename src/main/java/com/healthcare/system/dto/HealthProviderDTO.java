package com.healthcare.system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class HealthProviderDTO implements ObjectDTO {
    private Integer id;
    private String name;
    private String password;
    private String email;
    private String sessionId;
}
