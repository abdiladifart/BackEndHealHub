package com.example.backendhealhub.repository;

// AppointmentRepository.java
import com.example.backendhealhub.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    boolean existsByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);

//    List<Appointment> findByDoctorId(Long doctorId);

    Collection<Object> findByDoctorIdAndDateTime(Long doctorId, LocalDateTime dateTime);
}
