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
import java.util.Set;
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

        // Linking clinics to the specialty
        if (specialtyDTO.getClinicIds() != null && !specialtyDTO.getClinicIds().isEmpty()) {
            Set<Clinic> clinics = specialtyDTO.getClinicIds().stream()
                    .map(clinicId -> clinicRepository.findById(clinicId)
                            .orElseThrow(() -> new RuntimeException("Clinic not found for ID: " + clinicId)))
                    .collect(Collectors.toSet());
            specialty.setClinics(clinics);
        }

        Specialty savedSpecialty = specialtyRepository.save(specialty);
        return modelMapper.map(savedSpecialty, SpecialtyDTO.class);
    }
    @Override
    public SpecialtyDTO updateSpecialty(Long id, SpecialtyDTO specialtyDTO) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found."));

        // Manually update fields to avoid overwriting the ID
        specialty.setName(specialtyDTO.getName());

        // Updating clinic associations, similar to the create method
        if (specialtyDTO.getClinicIds() != null && !specialtyDTO.getClinicIds().isEmpty()) {
            Set<Clinic> clinics = specialtyDTO.getClinicIds().stream()
                    .map(clinicId -> clinicRepository.findById(clinicId)
                            .orElseThrow(() -> new RuntimeException("Clinic not found for ID: " + clinicId)))
                    .collect(Collectors.toSet());
            specialty.setClinics(clinics);
        }

        Specialty updatedSpecialty = specialtyRepository.save(specialty);
        return modelMapper.map(updatedSpecialty, SpecialtyDTO.class);
    }

    @Override
    public List<SpecialtyDTO> getAllSpecialties() {
        return specialtyRepository.findAll().stream()
                .map(specialty -> {
                    SpecialtyDTO dto = modelMapper.map(specialty, SpecialtyDTO.class);
                    List<Long> clinicIds = specialty.getClinics().stream()
                            .map(Clinic::getId)
                            .collect(Collectors.toList());
                    dto.setClinicIds(clinicIds);
                    return dto;
                })
                .collect(Collectors.toList());
    }



    @Override
    public SpecialtyDTO getSpecialtyById(Long id) {
        Specialty specialty = specialtyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Specialty not found."));
        return modelMapper.map(specialty, SpecialtyDTO.class);
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
