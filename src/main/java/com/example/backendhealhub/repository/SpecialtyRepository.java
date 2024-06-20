package com.example.backendhealhub.repository;

import com.example.backendhealhub.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    // Find specialties by clinic ID
    @Query("SELECT s FROM Specialty s JOIN s.clinics c WHERE c.id = :clinicId")
    List<Specialty> findByClinicId(Long clinicId);

    // Find specialties offered in a specific city
    @Query("SELECT s FROM Specialty s JOIN s.clinics c WHERE c.city = :city")
    List<Specialty> findByCity(String city);
}
