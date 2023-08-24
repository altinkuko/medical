package com.example.medical_center.controllers;

import com.example.medical_center.entities.Doctor;
import com.example.medical_center.services.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping("/doctor/create")
    public Doctor createDoctor(@RequestBody Doctor doctor, @RequestParam String username){
        return doctorService.createDoctor(doctor,username);
    }
    @PutMapping("/doctor/update")
    public Doctor updateDoctor(@RequestBody Doctor doctor, @RequestParam String username){
        return doctorService.updateDoctor(doctor, username);
    }
    @DeleteMapping("/doctor/delete")
    public void deleteDoctor(@RequestParam Long doctorId, String username){
        doctorService.deleteDoctor(doctorId, username);
    }

    @GetMapping("/doctor/all")
    public List<Doctor> getAll(){
        return doctorService.getAll();
    }

    @GetMapping("/doctor/search")
    public List<Doctor> search(@RequestParam String specialization){
        return doctorService.findBySpecialization(specialization);
    }

}
