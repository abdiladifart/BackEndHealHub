package com.example.backendhealhub.entity;


import java.util.Set;
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
    @Getter @Setter
    private Set<Doctor> doctors;



}

