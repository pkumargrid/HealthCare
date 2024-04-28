package com.healthcare.system.dto;

import com.healthcare.system.entities.Complaint;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReasonDTO {

    private Integer id;
    private String text;

    private Complaint complaint;

    private Integer doctorId;
}
