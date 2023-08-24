package com.example.medical_center.controllers;

import com.example.medical_center.entities.Employee;
import com.example.medical_center.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/create")
    public Employee createEmployee(@RequestBody Employee employee, @RequestParam String username){
        return employeeService.createEmployee(employee, username);
    }

    @PutMapping("/update")
    public Employee updateEmployee(@RequestBody Employee employee, @RequestParam String username){
        return employeeService.updateEmployee(employee, username);
    }

    @DeleteMapping("/delete")
    public void deleteEmployee(@RequestParam Long employeeId, @RequestParam String username){
        employeeService.deleteEmployee(employeeId, username);
    }

    @GetMapping("/all")
    public List<Employee> getAll(){
        return employeeService.getAll();
    }
}
