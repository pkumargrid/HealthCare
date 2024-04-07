package com.healthcare.system.controllers.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseCrudDTO {
    public String successful;

    public ResponseCrudDTO(String successful) {
        this.successful = successful;
    }

}
