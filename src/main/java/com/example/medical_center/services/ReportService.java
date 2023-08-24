package com.example.medical_center.services;

import com.example.medical_center.entities.Appointment;
import com.example.medical_center.entities.Report;
import com.example.medical_center.repositories.AppointmentRepository;
import com.example.medical_center.repositories.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;

    public Report createReport(Report report, Long appointmentId){
        Optional<Appointment> appointment = appointmentRepository.findById(appointmentId);
        if (appointment.isPresent()){
            report.setAppointment(appointment.get());
            reportRepository.save(report);
            return report;
        } else {
            throw new RuntimeException("Appointment does not exists");
        }
    }

    public Report updateReport(Report report){
        Optional<Report> existingReport = reportRepository.findById(report.getReportId());
        if (existingReport.isPresent()){
            report.setAppointment(existingReport.get().getAppointment());
            reportRepository.save(report);
            return report;
        } else {
            throw new RuntimeException("Report does not exists");
        }
    }

    public void deleteReport(Long id){
        reportRepository.deleteById(id);
    }

    public List<Report> getAll() {
        return reportRepository.findAll();
    }
}
