package com.example.backendhealhub.controller;

import com.example.backendhealhub.dto.AppointmentDTO;
import com.example.backendhealhub.service.AppointmentService;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.List;

@RestController
@RequestMapping("/appointments")
@Tag(name = "Appointment", description = "API for managing appointments")
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;


    @Operation(summary = "Book an appointment", description = "Books a new appointment with details provided in the request")
    @ApiResponse(responseCode = "200", description = "Appointment booked successfully", content = @Content(schema = @Schema(implementation = AppointmentDTO.class)))
    @PostMapping("/book")
    public ResponseEntity<AppointmentDTO> bookAppointment(@RequestBody AppointmentDTO appointmentDTO) {
        return ResponseEntity.ok(appointmentService.bookAppointment(appointmentDTO));
    }

//    @GetMapping("/user/{userId}")
//    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUser(@PathVariable Long userId) {
//        return ResponseEntity.ok(appointmentService.getAppointmentsByUserId(userId));
//    }


    @GetMapping("/user/{userId}")
    @Operation(summary = "List appointments by user", description = "Fetches a list of appointments associated with a specific user ID")
    @ApiResponse(responseCode = "200", description = "Appointments fetched successfully", content = @Content(schema = @Schema(implementation = AppointmentDTO.class)))
    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByUserId(@PathVariable Long userId) {
        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByUserId(userId);
        return ResponseEntity.ok(appointments);
    }

//    @GetMapping("/doctor/{doctorId}")
//    public ResponseEntity<List<AppointmentDTO>> getAppointmentsByDoctorId(@PathVariable Long doctorId) {
//        List<AppointmentDTO> appointments = appointmentService.getAppointmentsByDoctorId(doctorId);
//        return ResponseEntity.ok(appointments);
//    }



    @DeleteMapping("/cancel/{id}")
    @Operation(summary = "Cancel an appointment", description = "Cancels the appointment with the given ID")
    @ApiResponse(responseCode = "200", description = "Appointment cancelled successfully")
    public ResponseEntity<?> cancelAppointment(@PathVariable Long id) {
        appointmentService.cancelAppointment(id);
        return ResponseEntity.ok().build();
    }
}
