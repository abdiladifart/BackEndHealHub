package com.example.backendhealhub.service;

import com.example.backendhealhub.dto.AppointmentDTO;
import com.example.backendhealhub.entity.Appointment;
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

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(AppointmentServiceImpl.class);

    @Override
    public AppointmentDTO bookAppointment(AppointmentDTO appointmentDTO) {
        if (appointmentDTO.getUserId() == null || appointmentDTO.getDoctorId() == null ||
                appointmentDTO.getClinicId() == null || appointmentDTO.getSpecialtyId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "All IDs must be non-null for booking");
        }

        boolean isAvailable = isAppointmentAvailable(appointmentDTO.getDoctorId(), appointmentDTO.getDateTime());
        if (!isAvailable) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This appointment slot is already booked.");
        }

        logger.info("Received booking request with details: {}", appointmentDTO);

        Appointment appointment = new Appointment();
        appointment.setUser(userRepository.findById(appointmentDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found")));
        appointment.setDoctor(doctorRepository.findById(appointmentDTO.getDoctorId())
                .orElseThrow(() -> new RuntimeException("Doctor not found")));
        appointment.setClinic(clinicRepository.findById(appointmentDTO.getClinicId())
                .orElseThrow(() -> new RuntimeException("Clinic not found")));
        appointment.setSpecialty(specialtyRepository.findById(appointmentDTO.getSpecialtyId())
                .orElseThrow(() -> new RuntimeException("Specialty not found")));
        appointment.setDateTime(appointmentDTO.getDateTime());
        appointment = appointmentRepository.save(appointment);

        try {
            String subject = "Appointment Confirmation";
            String message = "Your appointment has been booked.";
            emailService.sendEmail(appointment.getUser().getEmail(), subject, message);
        } catch (RuntimeException e) {
            logger.error("Error sending email for appointment: {}", e.getMessage());
        }

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
                    dto.setLocation(a.getClinic().getCity());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public boolean isAppointmentAvailable(Long doctorId, LocalDateTime dateTime) {
        return appointmentRepository.findByDoctorIdAndDateTime(doctorId, dateTime).isEmpty();
    }

    @Override
    public void cancelAppointment(Long appointmentId) {
        appointmentRepository.deleteById(appointmentId);
    }

    @Override
    public List<AppointmentDTO> findAll() {
        return appointmentRepository.findAll().stream()
                .map(appointment -> {
                    if (appointment.getUser() == null || appointment.getDoctor() == null ||
                            appointment.getClinic() == null || appointment.getSpecialty() == null) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Appointment data incomplete.");
                    }
                    return new AppointmentDTO(
                            appointment.getId(),
                            appointment.getUser().getId(),
                            appointment.getDoctor().getId(),
                            appointment.getClinic().getId(),
                            appointment.getSpecialty().getId(),
                            appointment.getDateTime()
                    );
                })
                .collect(Collectors.toList());
    }

}
