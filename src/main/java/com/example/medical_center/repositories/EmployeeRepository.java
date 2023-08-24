package com.example.medical_center.repositories;

import com.example.medical_center.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("select employee from Employee employee where employee.username = :username")
    Employee findByUsername(@Param(value = "username") String username);

    Boolean existsByUsername(String username);
}
