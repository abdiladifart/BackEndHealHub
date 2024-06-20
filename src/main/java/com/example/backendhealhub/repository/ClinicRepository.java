package com.example.backendhealhub.repository;
import com.example.backendhealhub.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    // Example to find clinics by specialty name
    @Query("SELECT c FROM Clinic c JOIN c.specialties s WHERE s.id = :specialtyId AND c.city = :city AND c.region = :region")
    List<Clinic> findBySpecialtyIdAndCityAndRegion(Long specialtyId, String city, String region);
}

