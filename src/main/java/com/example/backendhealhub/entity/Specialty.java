package com.example.backendhealhub.entity;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "specialties")
public class Specialty {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "clinic_specialty",
            joinColumns = @JoinColumn(name = "specialty_id"),
            inverseJoinColumns = @JoinColumn(name = "clinic_id")
    )
    @Getter @Setter
    @JsonManagedReference
    private Set<Clinic> clinics;



    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    @Getter @Setter
    private String name;



}

