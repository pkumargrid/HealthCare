package com.healthcare.system.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseCrudDTO<T> {
    public String successful;

    @JsonIgnore
    public int status;

    public T objects;
    public ResponseCrudDTO(String successful, int status, T objects) {
        this.successful = successful;
        this.status = status;
        this.objects = objects;
    }

}
