package com.example.backendhealhub.controller;

import com.example.backendhealhub.dto.DoctorDTO;
import com.example.backendhealhub.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/doctors")
@Tag(name = "Doctors", description = "APIs for managing doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    // Get All Doctors

    @Operation(summary = "Get all doctors", description = "Retrieves all doctors")
    @ApiResponse(responseCode = "200", description = "All doctors retrieved successfully", content = @Content(schema = @Schema(implementation = DoctorDTO.class)))
    @GetMapping
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        List<DoctorDTO> doctors = doctorService.findAll();
        return ResponseEntity.ok(doctors);
    }

    // Get Doctors by Clinic ID
    @GetMapping("/byClinic/{clinicId}")
    @Operation(summary = "Get doctors by clinic ID", description = "Retrieves doctors based on the clinic ID")
    @ApiResponse(responseCode = "200", description = "Doctors retrieved successfully", content = @Content(schema = @Schema(implementation = DoctorDTO.class)))
    public ResponseEntity<List<DoctorDTO>> getDoctorsByClinic(@PathVariable Long clinicId) {
        List<DoctorDTO> doctors = doctorService.findDoctorsByClinicId(clinicId);
        return doctors.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(doctors);
    }

    // Get Doctor by ID
    @GetMapping("/{id}")
    @Operation(summary = "Get a doctor by ID", description = "Retrieves a doctor by their ID")
    @ApiResponse(responseCode = "200", description = "Doctor retrieved successfully", content = @Content(schema = @Schema(implementation = DoctorDTO.class)))
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        DoctorDTO doctor = doctorService.findById(id);
        return ResponseEntity.ok(doctor);
    }

    // Create a New Doctor
    @PostMapping
    @Operation(summary = "Create a new doctor", description = "Creates a new doctor with the provided details")
    @ApiResponse(responseCode = "200", description = "Doctor created successfully", content = @Content(schema = @Schema(implementation = DoctorDTO.class)))
    public ResponseEntity<DoctorDTO> createDoctor(@Valid @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO newDoctor = doctorService.create(doctorDTO);
        return ResponseEntity.ok(newDoctor);
    }

    // Update Existing Doctor
    @PutMapping("/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @Valid @RequestBody DoctorDTO doctorDTO) {
        DoctorDTO updatedDoctor = doctorService.update(id, doctorDTO);
        return ResponseEntity.ok(updatedDoctor);
    }

    // Delete Doctor by ID
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a doctor by ID", description = "Deletes a doctor based on their ID")
    @ApiResponse(responseCode = "204", description = "Doctor deleted successfully")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
