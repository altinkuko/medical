package com.example.medical_center.repositories;

import com.example.medical_center.entities.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {
    Report findByAppointment_AppointmentId(Long appointmentId);
}
