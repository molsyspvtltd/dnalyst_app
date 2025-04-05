package com.example.app_server.Roles;

import com.example.app_server.Roles.RoleUser;
import com.example.app_server.Roles.RoleUserRepository;
import org.springframework.stereotype.Service;

import javax.management.relation.Role;
import java.util.List;

@Service
public class StaffService {
    private final RoleUserRepository roleuserRepository;

    public StaffService(RoleUserRepository roleuserRepository) {
        this.roleuserRepository = roleuserRepository;
    }

    public List<RoleUser> getAllStaff() {
        return roleuserRepository.findAllStaff();
    }
}
