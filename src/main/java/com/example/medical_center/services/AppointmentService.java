package com.example.medical_center.services;

import com.example.medical_center.entities.Appointment;
import com.example.medical_center.entities.Doctor;
import com.example.medical_center.entities.Report;
import com.example.medical_center.repositories.AppointmentRepository;
import com.example.medical_center.repositories.DoctorRepository;
import com.example.medical_center.repositories.ReportRepository;
import com.example.medical_center.static_data.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private DoctorRepository doctorRepository;
    @Autowired
    private ReportRepository reportRepository;

    public Appointment createAppointment(Appointment appointment, String username, Long doctorId){
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (appointment.getBeginAt().isAfter(appointment.getEndAt())
                || appointment.getBeginAt().isEqual(appointment.getEndAt())){
            throw new RuntimeException("Begins must be before Ends");
        }
        if (doctor.isPresent() && employeeService.isEmployee(username)){
            List<Appointment> appointments = doctor.get().getAppointments();
            for (Appointment app:appointments) {
                if ((appointment.getBeginAt().isAfter(app.getBeginAt())
                        && appointment.getBeginAt().isBefore(app.getEndAt()))
                || (appointment.getEndAt().isAfter(app.getBeginAt())
                && appointment.getEndAt().isBefore(app.getEndAt()))){
                    throw new RuntimeException("Please select another time");
                }
          }
            appointment.setDoctor(doctor.get());
            appointment.setStatus(Status.CREATED);
            appointmentRepository.save(appointment);
            return appointment;
        } else if (!employeeService.isEmployee(username)){
            throw employeeService.throwErrorNotEmployee();
        } else {
            throw new RuntimeException(String.format("Doctor with id %s does not exists", doctorId));
        }
    }

    public Appointment updateAppointment(Appointment appointment, String username, Long doctorId){
        Optional<Doctor> doctor = doctorRepository.findById(doctorId);
        if (doctor.isPresent()) {
            if (employeeService.isEmployee(username)) {
                Appointment existingAppointment = appointmentRepository.findById(appointment.getAppointmentId())
                        .orElseThrow(() -> new RuntimeException("This Appointment does not exists"));
                appointment.setDoctor(doctor.get());
                appointmentRepository.save(appointment);
                return appointment;
            } else {
                throw employeeService.throwErrorNotEmployee();
            }
        } else {
            throw new RuntimeException("This doctor does not exists");
        }
    }

    public void deleteAppointment(Long id, String username){
        if (employeeService.isEmployee(username)){
            Report report = reportRepository.findByAppointment_AppointmentId(id);
            if (report != null){
                reportRepository.delete(report);
            }
            appointmentRepository.deleteById(id);
        } else {
            throw employeeService.throwErrorNotEmployee();
        }
    }

    public List<Appointment> getAll(){
        return appointmentRepository.findAll();
    }

    public List<Appointment> getByDoctorAndAfterNow(Long doctorId){
        LocalDateTime now = LocalDateTime.now();
        return appointmentRepository.findByDoctor_DoctorIdAndBeginAtAfter(doctorId, now);
    }
}
