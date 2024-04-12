package com.example.backendhealhub.repository;
import com.example.backendhealhub.entity.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository<Clinic, Long> {
    List<Clinic> findBySpecialtyIdAndCityAndRegion(Long specialtyId, String city, String region);


}
