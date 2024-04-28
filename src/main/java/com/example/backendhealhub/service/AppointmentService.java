package com.example.backendhealhub.service;

import com.example.backendhealhub.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO);
    List<AppointmentDTO> getAppointmentsByUserId(Long userId);
    void cancelAppointment(Long appointmentId);

//    List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId);
}
