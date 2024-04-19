package com.example.backendhealhub.dto;

import com.example.backendhealhub.entity.Appointment;
import com.example.backendhealhub.entity.Clinic;
import com.example.backendhealhub.entity.Specialty;
import com.example.backendhealhub.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Set;

public class DoctorDTO {

    private Long id;

    @Getter @Setter

    private String name;

    @Getter @Setter
    private Specialty specialty;

    @Getter @Setter
    private Set<Appointment> appointments;

    @Getter @Setter
    private User user; // Link to the User entity

    @Getter @Setter
    private Clinic clinic;


}
