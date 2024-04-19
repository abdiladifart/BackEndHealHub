package com.example.backendhealhub.service;


import com.example.backendhealhub.dto.DoctorDTO;
import java.util.List;

public interface DoctorService {
    List<DoctorDTO> findAll();
    List<DoctorDTO> findDoctorsByClinicId(Long clinicId);

    DoctorDTO findById(Long id);

    DoctorDTO create(DoctorDTO doctorDTO);

    DoctorDTO update(Long id, DoctorDTO doctorDTO);

    void delete(Long id);
    // Define other methods that you would like to implement
}
