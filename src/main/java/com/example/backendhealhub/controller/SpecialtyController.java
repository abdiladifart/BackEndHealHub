package com.example.backendhealhub.controller;
import com.example.backendhealhub.dto.SpecialtyDTO;
import com.example.backendhealhub.service.SpecialtyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/specialties")
public class SpecialtyController {

    @Autowired
    private SpecialtyService specialtyService;

    @PostMapping
    public ResponseEntity<SpecialtyDTO> createSpecialty(@RequestBody SpecialtyDTO specialtyDTO) {
        SpecialtyDTO createdSpecialty = specialtyService.createSpecialty(specialtyDTO);
        return new ResponseEntity<>(createdSpecialty, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SpecialtyDTO>> getAllSpecialties() {
        System.out.println("Fetching all specialties");  // This line will log in your console
        return ResponseEntity.ok(specialtyService.getAllSpecialties());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> getSpecialtyById(@PathVariable Long id) {
        return ResponseEntity.ok(specialtyService.getSpecialtyById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SpecialtyDTO> updateSpecialty(@PathVariable Long id, @RequestBody SpecialtyDTO specialtyDTO) {
        return ResponseEntity.ok(specialtyService.updateSpecialty(id, specialtyDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSpecialty(@PathVariable Long id) {
        specialtyService.deleteSpecialty(id);
        return ResponseEntity.ok().build();
    }
}
