package com.example.backendhealhub.service;

// AppointmentServiceImpl.java
import com.example.backendhealhub.dto.AppointmentDTO;
import com.example.backendhealhub.entity.Appointment;
import com.example.backendhealhub.entity.User;
import com.example.backendhealhub.repository.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ClinicRepository clinicRepository;
    @Autowired
    private SpecialtyRepository specialtyRepository;
    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Override
    public AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO) {

        boolean isAvailable = isAppointmentAvailable(appointmentDTO.getDoctorId(), appointmentDTO.getDateTime());
        if (!isAvailable) {
            // Throw an exception that translates to a meaningful HTTP status code
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This appointment slot is already booked.");
        }
        logger.info("Received booking request with details: {}", appointmentDTO);

//        if (!isAppointmentAvailable(appointmentDTO.getDoctorId(), appointmentDTO.getDateTime())) {
//            // Handle the case where the appointment is not available
//            // You could throw a custom exception that you can handle in your controller
//            throw new Exception("Appointment is already booked.");
//        }

        if (appointmentDTO.getUserId() == null || appointmentDTO.getDoctorId() == null ||
                appointmentDTO.getClinicId() == null || appointmentDTO.getSpecialtyId() == null) {
            throw new IllegalArgumentException("All IDs must be non-null for booking");
        }

        Appointment appointment = new Appointment();
        appointment.setUser(userRepository.findById(appointmentDTO.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
        appointment.setDoctor(doctorRepository.findById(appointmentDTO.getDoctorId()).orElseThrow(() -> new RuntimeException("Doctor not found")));
        appointment.setClinic(clinicRepository.findById(appointmentDTO.getClinicId()).orElseThrow(() -> new RuntimeException("Clinic not found")));
        appointment.setSpecialty(specialtyRepository.findById(appointmentDTO.getSpecialtyId()).orElseThrow(() -> new RuntimeException("Specialty not found")));
        appointment.setDateTime(appointmentDTO.getDateTime());
        appointment = appointmentRepository.save(appointment);

        return new AppointmentDTO(appointment.getId(), appointment.getUser().getId(), appointment.getDoctor().getId(), appointment.getClinic().getId(), appointment.getSpecialty().getId(), appointment.getDateTime());
    }


    @Override
    public List<AppointmentDTO> getAppointmentsByUserId(Long userId) {
        return appointmentRepository.findAll().stream()
                .filter(a -> a.getUser().getId().equals(userId))
                .map(a -> {
                    AppointmentDTO dto = new AppointmentDTO(
                            a.getId(),
                            a.getUser().getId(),
                            a.getDoctor().getId(),
                            a.getClinic().getId(),
                            a.getSpecialty().getId(),
                            a.getDateTime()
                    );
                    dto.setClinicName(a.getClinic().getName());
                    dto.setDoctorName(a.getDoctor().getName());
                    dto.setDate(a.getDateTime().toLocalDate().toString());
                    dto.setTime(a.getDateTime().toLocalTime().toString());
                    dto.setLocation(a.getClinic().getCity()); // Assume Clinic entity has a location field
                    return dto;
                })
                .collect(Collectors.toList());
    }

//    public List<AppointmentDTO> getAppointmentsByDoctorId(Long doctorId) {
//        return appointmentRepository.findAll().stream()
//                .filter(a -> a.getDoctor().getId().equals(doctorId))
//                .map(a -> {
//                    User patient = a.getUser();
//                    // Make sure 'patient' is not null
//                    if (patient == null) {
//                        logger.error("Appointment ID " + a.getId() + " has no associated user.");
//                        return null; // or handle this case as you see fit
//                    }
//                    String patientName = patient.getUsername(); // This assumes getUsername() gives the patient's name
//                    AppointmentDTO dto = new AppointmentDTO(
//                            a.getId(),
//                            patient.getId(),
//                            a.getDoctor().getId(),
//                            a.getClinic().getId(),
//                            a.getSpecialty().getId(),
//                            a.getDateTime()
//                    );
//                    dto.setClinicName(a.getClinic().getName());
//                    dto.setDoctorName(a.getDoctor().getName());
//                    dto.setPatientName(patientName);
//                    dto.setDate(a.getDateTime().toLocalDate().toString());
//                    dto.setTime(a.getDateTime().toLocalTime().toString());
//                    dto.setLocation(a.getClinic().getCity());
//                    return dto;
//                })
//                .collect(Collectors.toList());
//    }





    public boolean isAppointmentAvailable(Long doctorId, LocalDateTime dateTime) {
        // You need to check if there's any appointment with the same doctorId and dateTime
        return appointmentRepository.findByDoctorIdAndDateTime(doctorId, dateTime).isEmpty();
    }


    @Override
    public void cancelAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }


}
