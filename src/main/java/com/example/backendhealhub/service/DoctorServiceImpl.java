package com.example.backendhealhub.service;

import com.example.backendhealhub.dto.DoctorDTO;
import com.example.backendhealhub.entity.Doctor;
import com.example.backendhealhub.entity.User;
import com.example.backendhealhub.repository.DoctorRepository;
import com.example.backendhealhub.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<DoctorDTO> findAll() {
        return doctorRepository.findAll()
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<DoctorDTO> findDoctorsByClinicId(Long clinicId) {
        return doctorRepository.findByClinicId(clinicId)
                .stream()
                .map(doctor -> modelMapper.map(doctor, DoctorDTO.class))
                .collect(Collectors.toList());
    }


    public DoctorDTO create(DoctorDTO doctorDTO) {
        if (doctorDTO.getUser() == null || doctorDTO.getUser().getId() == null) {
            throw new IllegalArgumentException("User ID is required");
        }

        User user = userRepository.findById(doctorDTO.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + doctorDTO.getUser().getId()));

        Doctor doctor = modelMapper.map(doctorDTO, Doctor.class);
        doctor.setUser(user);  // Set the user directly obtained from the repository
        doctor = doctorRepository.save(doctor);

        return modelMapper.map(doctor, DoctorDTO.class);
    }


    @Override
    public DoctorDTO findById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    @Override
    @Transactional
    public DoctorDTO update(Long id, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        doctor.setName(doctorDTO.getName());

        Doctor updatedDoctor = doctorRepository.save(doctor);
        return modelMapper.map(updatedDoctor, DoctorDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (!doctorRepository.existsById(id)) {
            throw new RuntimeException("Doctor not found");
        }
        doctorRepository.deleteById(id);
    }

}
