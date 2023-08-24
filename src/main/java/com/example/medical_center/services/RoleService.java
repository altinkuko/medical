package com.example.medical_center.services;

import com.example.medical_center.entities.Role;
import com.example.medical_center.repositories.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository){
        this.roleRepository = roleRepository;
    }

    @PostConstruct
    private void createRoles(){
        Optional<Role> role = roleRepository.findById("ADMIN");
        if (role.isEmpty()){
            Role roleEntity = new Role();
            roleEntity.setRole("ADMIN");
            roleRepository.save(roleEntity);
        }
        role = roleRepository.findById("EMPLOYEE");
        if (role.isEmpty()){
            Role roleEntity = new Role();
            roleEntity.setRole("EMPLOYEE");
            roleRepository.save(roleEntity);
        }
    }
}
