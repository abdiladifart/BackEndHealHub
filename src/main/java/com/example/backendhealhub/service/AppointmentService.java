package com.example.backendhealhub.service;

import com.example.backendhealhub.dto.AppointmentDTO;

import java.util.List;

public interface AppointmentService {
    AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO);
    List<AppointmentDTO> findAllAppointments();
    AppointmentDTO findAppointmentById(Long id);
    AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO);
    void deleteAppointment(Long id);
}
