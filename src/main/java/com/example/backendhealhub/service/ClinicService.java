package com.example.backendhealhub.service;
import com.example.backendhealhub.dto.ClinicDTO;

import java.util.List;

public interface ClinicService {
    ClinicDTO createClinic(ClinicDTO clinicDTO);
    List<ClinicDTO> getAllClinics();
    ClinicDTO getClinicById(Long id);
    ClinicDTO updateClinic(Long id, ClinicDTO clinicDTO);
    void deleteClinic(Long id);

    List<ClinicDTO> getClinicsBySpecialtyAndLocation(Long specialtyId, String city, String region);
}
