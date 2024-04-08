package com.example.backendhealhub.repository;
import com.example.backendhealhub.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    // Custom queries can be added here if needed, for example:
    // Optional<Doctor> findByUserId(Long userId);
}
