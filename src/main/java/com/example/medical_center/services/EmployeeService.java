package com.example.medical_center.services;

import com.example.medical_center.entities.Employee;
import com.example.medical_center.entities.Role;
import com.example.medical_center.repositories.EmployeeRepository;
import com.example.medical_center.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;
    @Autowired
    private RoleRepository roleRepository;

    public Employee createEmployee(Employee employee, String username){
        if (!employeeRepository.existsByUsername(employee.getUsername())) {
            if (isAdmin(username)) {
                Optional<Role> role = roleRepository.findById("EMPLOYEE");
                if (role.isPresent()) {
                    employee.setRole(role.get());
                    employeeRepository.save(employee);
                    return employee;
                }
            } else {
                throw throwErrorNotAdmin();
            }
        } else {
            throw new RuntimeException(String.format("Employee with username %s exists", employee.getUsername()));
        }
        return null;
    }

    public Employee updateEmployee(Employee employee, String username){
        if (isAdmin(username)){
            Optional<Employee> existingEmployee = employeeRepository.findById(employee.getEmployeeId());
            if (existingEmployee.isPresent()){
                employee.setRole(existingEmployee.get().getRole());
                employeeRepository.save(employee);
                return employee;
            } else {
                throw new RuntimeException("This employee does not exists");
            }
        } else {
            throw throwErrorNotAdmin();
        }
    }

    public void deleteEmployee(Long id, String username){
        if (isAdmin(username)){
            employeeRepository.deleteById(id);
        } else {
            throw throwErrorNotAdmin();
        }
    }

    public List<Employee> getAll(){
        return employeeRepository.findAll();
    }

    @PostConstruct
    private void createAdmin(){
        Employee employee = employeeRepository.findByUsername("Admin");
        if (employee == null){
            Employee admin = new Employee();
            admin.setUsername("Admin");
            Optional<Role> role = roleRepository.findById("ADMIN");
            admin.setRole(role.get());
            employeeRepository.save(admin);
        }
    }

    public Boolean isAdmin(String username){
        Employee employee = employeeRepository.findByUsername(username);
        return (employee != null && employee.getRole().getRole().equals("ADMIN"));
    }

    public Boolean isEmployee(String username){
        Employee employee = employeeRepository.findByUsername(username);
        return (employee != null && employee.getRole().getRole().equals("EMPLOYEE"));
    }
    public RuntimeException throwErrorNotAdmin(){
        return new RuntimeException("This user is not an Admin");
    }

    public RuntimeException throwErrorNotEmployee(){
        return new RuntimeException("This user is not an Employee");
    }
}
