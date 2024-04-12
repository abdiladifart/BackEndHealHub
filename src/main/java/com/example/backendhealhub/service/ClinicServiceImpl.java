package com.example.backendhealhub.service;

import com.example.backendhealhub.dto.ClinicDTO;
import com.example.backendhealhub.entity.Clinic;
import com.example.backendhealhub.repository.ClinicRepository;
import com.example.backendhealhub.service.ClinicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClinicServiceImpl implements ClinicService {

    @Autowired
    private ClinicRepository clinicRepository;

    @Autowired
    private ModelMapper modelMapper;

    public ClinicDTO createClinic(ClinicDTO clinicDTO) {
        Clinic clinic = new Clinic();
        clinic.setName(clinicDTO.getName());
        clinic.setCity(clinicDTO.getCity());
        clinic.setRegion(clinicDTO.getRegion());// Ensure this is set

        clinic = clinicRepository.save(clinic);
        return modelMapper.map(clinic, ClinicDTO.class); // Assuming you're using ModelMapper
    }


    @Override
    public List<ClinicDTO> getAllClinics() {
        return clinicRepository.findAll().stream()
                .map(clinic -> modelMapper.map(clinic, ClinicDTO.class))
                .collect(Collectors.toList());
    }

    public List<ClinicDTO> getClinicsBySpecialtyAndLocation(Long specialtyId, String city, String region) {
        List<Clinic> clinics = clinicRepository.findBySpecialtyIdAndCityAndRegion(specialtyId, city, region);
        return clinics.stream()
                .map(clinic -> modelMapper.map(clinic, ClinicDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ClinicDTO getClinicById(Long id) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found."));
        return modelMapper.map(clinic, ClinicDTO.class);
    }

    @Override
    public ClinicDTO updateClinic(Long id, ClinicDTO clinicDTO) {
        Clinic clinic = clinicRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Clinic not found."));
        modelMapper.map(clinicDTO, clinic);
        return modelMapper.map(clinicRepository.save(clinic), ClinicDTO.class);
    }

    @Override
    public void deleteClinic(Long id) {
        clinicRepository.deleteById(id);
    }
}
