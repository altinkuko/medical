package com.example.medical_center.controllers;

import com.example.medical_center.entities.Report;
import com.example.medical_center.services.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/report")
public class ReportController {
    @Autowired
    private ReportService reportService;

    @PostMapping("/create")
    public Report createReport(@RequestBody Report report, @RequestParam Long appointmentId){
        return reportService.createReport(report, appointmentId);
    }

    @PutMapping("/update")
    public Report updateReport(@RequestBody Report report){
        return reportService.updateReport(report);
    }

    @DeleteMapping("/delete")
    public void deleteReport(@RequestParam Long reportId){
        reportService.deleteReport(reportId);
    }

    @GetMapping("/all")
    public List<Report> getAll(){
        return reportService.getAll();
    }
}
