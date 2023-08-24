package com.example.medical_center.services;

import com.example.medical_center.entities.Doctor;
import com.example.medical_center.repositories.DoctorRepository;
import com.example.medical_center.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private EmployeeService employeeService;

    public Doctor createDoctor(Doctor doctor, String username) {
        Optional<Doctor> existingDoctor = doctorRepository.findByUsername(doctor.getUsername());
        if (existingDoctor.isEmpty()) {
            if (employeeService.isAdmin(username)) {
                return doctorRepository.save(doctor);
            } else {
                throw new RuntimeException("This user is not an Admin");
            }
        } else {
            throw new RuntimeException(String.format("Doctor with username %s exists", doctor.getUsername()));
        }
    }

    public Doctor updateDoctor(Doctor doctor, String username){
        Optional<Doctor> checkDoctor = doctorRepository.findByUsername(doctor.getUsername());
        if (checkDoctor.isEmpty() || checkDoctor.get().getDoctorId() == doctor.getDoctorId()) {
            if (employeeService.isAdmin(username)) {
                Optional<Doctor> existingDoctor = doctorRepository.findById(doctor.getDoctorId());
                if (existingDoctor.isPresent()) {
                    doctorRepository.save(doctor);
                    return doctor;
                } else {
                    throw new RuntimeException("This doctor does not exists");
                }
            } else {
                throw employeeService.throwErrorNotAdmin();
            }
        } else {
            throw new RuntimeException(String.format("Doctor with username %s exists", doctor.getUsername()));
        }
    }

    public void deleteDoctor(Long id, String username){
        Optional<Doctor> doctor = doctorRepository.findById(id);
        if (doctor.isPresent() && !doctor.get().getAppointments().isEmpty()){
            throw new RuntimeException("This doctor cannot be deleted");
        }
        if (employeeService.isAdmin(username)){
            doctorRepository.deleteById(id);
        } else {
            throw employeeService.throwErrorNotAdmin();
        }
    }

    public List<Doctor> getAll(){
        return doctorRepository.findAll();
    }

    public List<Doctor> findBySpecialization(String specialization){
        List<Doctor> doctors = doctorRepository.findAllBySpecialization(specialization);
        if (doctors.isEmpty()){
            throw new RuntimeException("There are not doctors for this specialization");
        } else {
            return doctors;
        }
    }
}
