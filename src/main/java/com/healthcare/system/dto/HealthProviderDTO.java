package com.healthcare.system.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class HealthProviderDTO implements ObjectDTO {
    private String name;
    private String password;
    private String email;
    private String sessionId;
}
