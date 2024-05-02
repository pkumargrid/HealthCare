package com.healthcare.system.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Reason {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String text;

    @OneToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST})
    @JoinColumn(name = "reason_id")
    @JsonManagedReference
    private Complaint complaint;

}
