package com.example.backendhealhub.controller;

import com.example.backendhealhub.dto.ClinicDTO;
import com.example.backendhealhub.service.ClinicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clinics")
public class ClinicController {

    @Autowired
    private ClinicService clinicService;

    @PostMapping
    public ResponseEntity<ClinicDTO> createClinic(@RequestBody ClinicDTO clinicDTO) {
        return ResponseEntity.ok(clinicService.createClinic(clinicDTO));
    }

    @GetMapping
    public ResponseEntity<List<ClinicDTO>> getAllClinics() {
        return ResponseEntity.ok(clinicService.getAllClinics());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClinicDTO> getClinicById(@PathVariable Long id) {
        return ResponseEntity.ok(clinicService.getClinicById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClinicDTO> updateClinic(@PathVariable Long id, @RequestBody ClinicDTO clinicDTO) {
        return ResponseEntity.ok(clinicService.updateClinic(id, clinicDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClinic(@PathVariable Long id) {
        clinicService.deleteClinic(id);
        return ResponseEntity.ok().build();
    }
}
