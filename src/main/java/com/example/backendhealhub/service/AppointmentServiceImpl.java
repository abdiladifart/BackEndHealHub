package com.example.backendhealhub.service;
import com.example.backendhealhub.dto.AppointmentDTO;
import com.example.backendhealhub.entity.Appointment;
import com.example.backendhealhub.entity.Doctor;
import com.example.backendhealhub.entity.User;
import com.example.backendhealhub.repository.AppointmentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    private ModelMapper modelMapper = new ModelMapper();

//    @Override
//    public AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) {
//        // Conversion from DTO to entity
//        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);
//        // Saving the entity
//        Appointment savedAppointment = appointmentRepository.save(appointment);
//        // Conversion back to DTO
//        return modelMapper.map(savedAppointment, AppointmentDTO.class);
//    }

    @Override
    public AppointmentDTO saveAppointment(AppointmentDTO appointmentDTO) {
        Appointment appointment = modelMapper.map(appointmentDTO, Appointment.class);
        appointment.setClinic(appointmentDTO.getClinic()); // Set clinic explicitly
        Appointment savedAppointment = appointmentRepository.save(appointment);
        return modelMapper.map(savedAppointment, AppointmentDTO.class);
    }
    @Override
    public List<AppointmentDTO> findAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public AppointmentDTO findAppointmentById(Long id) {
        return appointmentRepository.findById(id)
                .map(appointment -> modelMapper.map(appointment, AppointmentDTO.class))
                .orElse(null);
    }

    @Override
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO appointmentDTO) {
        return appointmentRepository.findById(id).map(existingAppointment -> {
            // Update the necessary fields
            existingAppointment.setDoctor(modelMapper.map(appointmentDTO.getDoctor(), Doctor.class));
            existingAppointment.setUser(modelMapper.map(appointmentDTO.getUser(), User.class));
            existingAppointment.setAppointmentTime(appointmentDTO.getAppointmentTime());
            existingAppointment.setStatue(appointmentDTO.getStatue());
            existingAppointment.setClinic(appointmentDTO.getClinic()); // Update clinic explicitly
            Appointment updatedAppointment = appointmentRepository.save(existingAppointment);
            return modelMapper.map(updatedAppointment, AppointmentDTO.class);
        }).orElse(null); // Handle case where appointment doesn't exist
    }

    @Override
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
}
