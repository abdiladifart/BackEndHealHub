package com.example.backendhealhub.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import javax.persistence.*;

@Entity
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter
    @Setter
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    @Getter
    @Setter
    private User user;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    @Getter
    @Setter
    @JsonBackReference
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "clinic_id")
    @Getter
    @Setter
    private Clinic clinic;

    @ManyToOne
    @JoinColumn(name = "specialty_id")
    @Getter
    @Setter
    private Specialty specialty;

    @Getter
    @Setter
    @Column(name = "date_time")
    private LocalDateTime dateTime;

    // Constructors, getters, and setters
}

// AppointmentDTO.java (Data Transfer Object)

