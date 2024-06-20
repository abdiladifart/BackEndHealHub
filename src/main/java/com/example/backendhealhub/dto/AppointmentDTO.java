package com.example.backendhealhub.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

public class AppointmentDTO {
    @Getter
    @Setter
    private Long id;
    @Getter
    @Setter
    private Long userId;
    @Getter
    @Setter
    private Long doctorId;
    @Getter
    @Setter
    private Long clinicId;
    @Getter
    @Setter
    private Long specialtyId;
    @Getter
    @Setter
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateTime;
    @Getter
    @Setter
    private String clinicName;
    @Getter
    @Setter
    private String doctorName;
    @Getter
    @Setter
    private String date;
    @Getter
    @Setter
    private String time;
    @Getter
    @Setter
    private String location;

    public AppointmentDTO(Long id, Long userId, Long doctorId, Long clinicId, Long specialtyId, LocalDateTime dateTime) {
        this.id = id;
        this.userId = userId;
        this.doctorId = doctorId;
        this.clinicId = clinicId;
        this.specialtyId = specialtyId;
        this.dateTime = dateTime;
    }
}
