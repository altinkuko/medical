package com.example.medical_center.repositories;

import com.example.medical_center.entities.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    List<Appointment> findByDoctor_DoctorIdAndBeginAtAfter(Long doctorId, LocalDateTime now);
}
