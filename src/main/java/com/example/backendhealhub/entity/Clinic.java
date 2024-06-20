package com.example.backendhealhub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @Getter
    @Setter
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

    @JoinColumn(name = "latitude")
    @Getter @Setter
    private double latitude;

    @JoinColumn(name = "longitude")
    @Getter @Setter
    private double longitude;


    @ManyToMany(mappedBy = "clinics")
    @Getter @Setter
    @JsonBackReference
    private Set<Specialty> specialties;

}
