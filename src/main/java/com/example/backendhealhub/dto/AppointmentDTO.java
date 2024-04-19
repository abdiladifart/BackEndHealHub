package com.example.backendhealhub.dto;

import com.example.backendhealhub.entity.Clinic;
import com.example.backendhealhub.entity.Doctor;
import com.example.backendhealhub.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class AppointmentDTO {

    @Getter @Setter
    private Long id;

    @Getter @Setter
    private User user;

    @Getter @Setter
    private Doctor doctor;

    @Getter @Setter
    private LocalDateTime appointmentTime;

    @Getter @Setter
    private String Statue;

    @Getter @Setter
    private Clinic clinic;


//    public AppointmentDTO(Long id, User user, Doctor doctor, LocalDateTime appointmentTime, String statue) {
//        this.id = id;
//        this.user = user;
//        this.doctor = doctor;
//        this.appointmentTime = appointmentTime;
//        Statue = statue;
//    }

    public AppointmentDTO(Long id, User user, Doctor doctor, LocalDateTime appointmentTime, String statue, Clinic clinic) {
        this.id = id;
        this.user = user;
        this.doctor = doctor;
        this.appointmentTime = appointmentTime;
        this.Statue = statue;
        this.clinic = clinic;
    }
}
