package com.example.backendhealhub.controller;
import com.example.backendhealhub.dto.SpecialtyDTO;
import com.example.backendhealhub.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/specialties")
@Tag(name = "Specialties", description = "APIs for managing specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @PostMapping
    @Operation(summary = "Create a specialty", description = "Creates a new specialty with the given details")
    @ApiResponse(responseCode = "201", description = "Specialty created successfully", content = @Content(schema = @Schema(implementation = SpecialtyDTO.class)))
    public ResponseEntity<SpecialtyDTO> createSpecialty(@RequestBody SpecialtyDTO specialtyDTO) {
        SpecialtyDTO createdSpecialty = specialtyService.createSpecialty(specialtyDTO);
        return new ResponseEntity<>(createdSpecialty, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Get all specialties", description = "Retrieves a list of all specialties")
    @ApiResponse(responseCode = "200", description = "All specialties retrieved successfully", content = @Content(schema = @Schema(implementation = List.class)))
    public ResponseEntity<List<SpecialtyDTO>> getAllSpecialties() {
        return ResponseEntity.ok(specialtyService.getAllSpecialties());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a specialty by ID", description = "Retrieves a specialty by its ID")
    @ApiResponse(responseCode = "200", description = "Specialty retrieved successfully", content = @Content(schema = @Schema(implementation = SpecialtyDTO.class)))
    public ResponseEntity<SpecialtyDTO> getSpecialtyById(@PathVariable Long id) {
        SpecialtyDTO specialty = specialtyService.getSpecialtyById(id);
        return ResponseEntity.ok(specialty);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a specialty", description = "Updates the details of an existing specialty")
    @ApiResponse(responseCode = "200", description = "Specialty updated successfully", content = @Content(schema = @Schema(implementation = SpecialtyDTO.class)))
    public ResponseEntity<SpecialtyDTO> updateSpecialty(@PathVariable Long id, @RequestBody SpecialtyDTO specialtyDTO) {
        SpecialtyDTO updatedSpecialty = specialtyService.updateSpecialty(id, specialtyDTO);
        return ResponseEntity.ok(updatedSpecialty);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a specialty", description = "Deletes a specialty by its ID")
    @ApiResponse(responseCode = "200", description = "Specialty deleted successfully")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id) {
        specialtyService.deleteSpecialty(id);
        return ResponseEntity.ok().build();
    }
}
