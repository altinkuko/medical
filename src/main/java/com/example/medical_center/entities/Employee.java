package com.example.medical_center.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "user")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long employeeId;

    @Column(name = "username", unique = true)
    private String username;
    @ManyToOne
    @JoinColumn(name = "role")
    private Role role;

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
