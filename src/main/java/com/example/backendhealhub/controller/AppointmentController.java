package com.example.backendhealhub.controller;
import com.example.backendhealhub.dto.AppointmentDTO;
import com.example.backendhealhub.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    // Create a new appointment
    @PostMapping
    public ResponseEntity<AppointmentDTO> createAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return new ResponseEntity<>(appointmentService.saveAppointment(appointmentDTO), HttpStatus.CREATED);
    }

    // Retrieve all appointments
    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.findAllAppointments());
    }

    // Retrieve a single appointment by ID
    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> getAppointmentById(@PathVariable Long id) {
        AppointmentDTO appointmentDTO = appointmentService.findAppointmentById(id);
        if (appointmentDTO != null) {
            return ResponseEntity.ok(appointmentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update an existing appointment
    @PutMapping("/{id}")
    public ResponseEntity<AppointmentDTO> updateAppointment(@PathVariable Long id, @RequestBody AppointmentDTO appointmentDTO) {
        AppointmentDTO updatedAppointmentDTO = appointmentService.updateAppointment(id, appointmentDTO);
        if (updatedAppointmentDTO != null) {
            return ResponseEntity.ok(updatedAppointmentDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete an appointment
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return ResponseEntity.noContent().build();
    }
}
