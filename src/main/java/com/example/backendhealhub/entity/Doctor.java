package com.example.backendhealhub.entity;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "doctors")
public class Doctor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter
    private Long id;

    @Getter @Setter
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "specialty_id", nullable = false)
    @JsonBackReference
    @Getter @Setter
    private Specialty specialty;

    @OneToMany(mappedBy = "doctor")
    @JsonManagedReference
    @Getter @Setter
    private Set<Appointment> appointments;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @Getter @Setter
    private User user; // Link to the User entity


    @ManyToOne
    @JoinColumn(name = "clinic_id", nullable = false) // Assuming each doctor works at one clinic
    @Getter @Setter
    private Clinic clinic;


}
