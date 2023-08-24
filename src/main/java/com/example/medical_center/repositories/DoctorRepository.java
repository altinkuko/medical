package com.example.medical_center.repositories;

import com.example.medical_center.entities.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByUsername(String username);
    @Query("select d from Doctor d where d.specialization = :specialization")
    List<Doctor> findAllBySpecialization(@Param(value = "specialization") String specialization);
}
