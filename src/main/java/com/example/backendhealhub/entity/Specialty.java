package com.example.backendhealhub.entity;


import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "specialties")
public class Specialty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    @Getter @Setter
    private String name;

    @OneToMany(mappedBy = "specialty")
    @JsonBackReference
    @Getter @Setter
    private Set<Doctor> doctors;

    @ManyToMany
    @JoinTable(
            name = "clinic_specialty",
            joinColumns = @JoinColumn(name = "specialty_id"),
            inverseJoinColumns = @JoinColumn(name = "clinic_id")
    )
    @Getter @Setter
    private Set<Clinic> clinics;

}

