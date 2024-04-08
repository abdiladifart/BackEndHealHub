package com.example.backendhealhub.entity;
import java.util.Set;
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
    @Getter @Setter
    private Specialty specialty;

    @OneToMany(mappedBy = "doctor")
    @Getter @Setter
    private Set<Appointment> appointments;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    @Getter @Setter
    private User user; // Link to the User entity

}
