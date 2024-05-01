package com.example.backendhealhub.controller;

import com.example.backendhealhub.dto.ClinicDTO;
import com.example.backendhealhub.service.ClinicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinics")
@Tag(name = "Clinics", description = "APIs for managing clinics")

public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @Operation(summary = "Create a clinic", description = "Creates a new clinic with the given details")
    @ApiResponse(responseCode = "200", description = "Clinic created successfully", content = @Content(schema = @Schema(implementation = ClinicDTO.class)))
    @PostMapping
    public ResponseEntity<ClinicDTO> createClinic(@RequestBody ClinicDTO clinicDTO) {
        ClinicDTO createdClinic = clinicService.createClinic(clinicDTO);
        return ResponseEntity.ok(createdClinic);
    }

    @GetMapping("/search")
    @Operation(summary = "Search clinics by specialty and location", description = "Retrieves clinics based on specialty ID and location")
    @ApiResponse(responseCode = "200", description = "Clinics retrieved successfully", content = @Content(schema = @Schema(implementation = ClinicDTO.class)))
    public ResponseEntity<List<ClinicDTO>> getClinicsBySpecialtyLocation(
            @RequestParam Long specialtyId,
            @RequestParam String city,
            @RequestParam String region) {
        List<ClinicDTO> clinics = clinicService.getClinicsBySpecialtyAndLocation(specialtyId, city, region);

        return ResponseEntity.ok(clinics);
    }

    @GetMapping
    @Operation(summary = "Get all clinics", description = "Retrieves all clinics")
    @ApiResponse(responseCode = "200", description = "All clinics retrieved successfully", content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<ClinicDTO>> getAllClinics() {
        List<ClinicDTO> clinics = clinicService.getAllClinics();
        return ResponseEntity.ok(clinics);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a clinic by ID", description = "Retrieves a clinic by its ID")
    @ApiResponse(responseCode = "200", description = "Clinic retrieved successfully", content = @Content(schema = @Schema(implementation = ClinicDTO.class)))
    public ResponseEntity<ClinicDTO> getClinicById(@PathVariable Long id) {
        ClinicDTO clinic = clinicService.getClinicById(id);
        return ResponseEntity.ok(clinic);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a clinic", description = "Updates the clinic with the specified ID")
    @ApiResponse(responseCode = "200", description = "Clinic updated successfully", content = @Content(schema = @Schema(implementation = ClinicDTO.class)))
    public ResponseEntity<ClinicDTO> updateClinic(@PathVariable Long id, @RequestBody ClinicDTO clinicDTO) {
        ClinicDTO updatedClinic = clinicService.updateClinic(id, clinicDTO);
        return ResponseEntity.ok(updatedClinic);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a clinic", description = "Deletes the clinic with the specified ID")
    @ApiResponse(responseCode = "200", description = "Clinic deleted successfully")
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        clinicService.deleteClinic(id);
        return ResponseEntity.ok().build();
    }
}
