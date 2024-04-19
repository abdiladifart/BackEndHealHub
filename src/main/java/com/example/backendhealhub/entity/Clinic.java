package com.example.backendhealhub.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "clinics")
public class Clinic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    @Getter @Setter
    private String name;

    @Getter @Setter
    @Column(nullable = false)
    private String city;

    @Getter @Setter
    @Column (nullable = false)
    private String region;

    @ManyToOne
    @JoinColumn(name = "specialty_id") // Assuming a clinic offers one specialty for simplicity
    @Getter @Setter
    private Specialty specialty;

}
