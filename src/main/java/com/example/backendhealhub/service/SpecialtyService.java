package com.example.backendhealhub.service;


import com.example.backendhealhub.dto.SpecialtyDTO;

import java.util.List;

public interface SpecialtyService {
    SpecialtyDTO createSpecialty(SpecialtyDTO specialtyDTO);
    List<SpecialtyDTO> getAllSpecialties();
    SpecialtyDTO getSpecialtyById(Long id);
    SpecialtyDTO updateSpecialty(Long id, SpecialtyDTO specialtyDTO);
    void deleteSpecialty(Long id);
}
