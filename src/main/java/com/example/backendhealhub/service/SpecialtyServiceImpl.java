package com.example.backendhealhub.service;
import com.example.backendhealhub.dto.ClinicDTO;
import com.example.backendhealhub.dto.SpecialtyDTO;
import com.example.backendhealhub.entity.Clinic;
import com.example.backendhealhub.entity.Specialty;
import com.example.backendhealhub.repository.ClinicRepository;
import com.example.backendhealhub.repository.SpecialtyRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpecialtyServiceImpl implements SpecialtyService {

    @Autowired
    private SpecialtyRepository specialtyRepository;

    @Autowired
    private ClinicRepository clinicRepository;


    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SpecialtyDTO createSpecialty(SpecialtyDTO specialtyDTO) {
        Specialty specialty = modelMapper.map(specialtyDTO, Specialty.class);
        return modelMapper.map(specialtyRepository.save(specialty), SpecialtyDTO.class);
    }

    @Override
    public List<SpecialtyDTO> getAllSpecialties() {
        return specialtyRepository.findAll().stream()
                .map(specialty -> modelMapper.map(specialty, SpecialtyDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public SpecialtyDTO getSpecialtyById(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found."));
        return modelMapper.map(specialty, SpecialtyDTO.class);
    }

    @Override
    public SpecialtyDTO updateSpecialty(Long id, SpecialtyDTO specialtyDTO) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found."));
        modelMapper.map(specialtyDTO, specialty);
        return modelMapper.map(specialtyRepository.save(specialty), SpecialtyDTO.class);
    }

    @Override
    public void deleteSpecialty(Long id) {
        specialtyRepository.deleteById(id);
    }

    public List<ClinicDTO> getClinicsBySpecialtyAndLocation(Long specialtyId, String city, String region) {
        List<Clinic> clinics = clinicRepository.findBySpecialtyIdAndCityAndRegion(specialtyId, city, region);
        return clinics.stream().map(clinic -> modelMapper.map(clinic, ClinicDTO.class)).collect(Collectors.toList());
    }
}
