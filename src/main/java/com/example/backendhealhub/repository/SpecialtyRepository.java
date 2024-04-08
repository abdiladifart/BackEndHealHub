package com.example.backendhealhub.repository;

import com.example.backendhealhub.entity.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialtyRepository extends JpaRepository<Specialty, Long> {
    // Here, you can define custom queries specific to your needs, if necessary.
}
