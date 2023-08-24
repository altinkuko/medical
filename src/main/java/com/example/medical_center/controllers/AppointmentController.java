package com.example.medical_center.controllers;

import com.example.medical_center.entities.Appointment;
import com.example.medical_center.services.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppointmentController {
    @Autowired
    private AppointmentService appointmentService;

    @PostMapping("/appointment/create")
    public Appointment createAppointment(@RequestParam String username, @RequestBody Appointment appointment, @RequestParam Long doctorId){
      return appointmentService.createAppointment(appointment, username, doctorId);
    }

    @PutMapping("/appointment/update")
    public Appointment updateAppointment(@RequestParam String username, @RequestBody Appointment appointment, @RequestParam Long doctorId) {
        return appointmentService.updateAppointment(appointment, username, doctorId);
    }
    @DeleteMapping("/appointment/delete")
    public void deleteAppointment(@RequestParam Long id, @RequestParam String username){
        appointmentService.deleteAppointment(id, username);
    }

    @GetMapping("appointment/all")
    public List<Appointment> getAll(){
        return appointmentService.getAll();
    }

    @GetMapping("/appointment/by_doctor")
    public List<Appointment> findByDoctor(@RequestParam Long doctorId){
        return appointmentService.getByDoctorAndAfterNow(doctorId);
    }
}
