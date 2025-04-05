package com.example.app_server.Roles;

import com.example.app_server.security.EncryptionUtil;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminService {

    private final RoleUserRepository roleuserRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(RoleUserRepository roleuserRepository, PasswordEncoder passwordEncoder) {
        this.roleuserRepository = roleuserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void createMasterAdminIfNotExists() {
        System.out.println("Checking for existing Master Admin...");

        String email = "admin@system.com";
        Optional<RoleUser> existingAdmin = roleuserRepository.findByEmail(email);

        if (existingAdmin.isEmpty()) {
            System.out.println("No Master Admin found. Creating...");

            RoleUser masterAdmin = new RoleUser();
            masterAdmin.setId("MST001");
            masterAdmin.setName("Master Admin");
            masterAdmin.setEmail(email);
            masterAdmin.setPhone(EncryptionUtil.encrypt("9999999999"));

            masterAdmin.setRole(Role.ADMIN);

            // Ensure password is encoded before saving
            String rawPassword = "admin@123";
            masterAdmin.setPassword(passwordEncoder.encode(rawPassword));

            roleuserRepository.save(masterAdmin);
            System.out.println("Master Admin created successfully!");
        } else {
            System.out.println("Master Admin already exists.");
        }
    }

    // Decrypt admin details when fetching
    public RoleUser getMasterAdmin() {
        String email = "admin@system.com";

        return roleuserRepository.findByEmail(email)
                .map(admin -> {
                    admin.setEmail(email);
//                    admin.setPhone(EncryptionUtil.decrypt(admin.getPhone()));
                    return admin;
                })
                .orElse(null);
    }

    public RoleUser createSubAdmin(RoleUser roleuser, Role subAdminRole) {
        if (!isValidSubAdminRole(subAdminRole)) {
            throw new IllegalArgumentException("Invalid role for Sub-Admin.");
        }

        String prefix = getRolePrefix(subAdminRole);
        String nextId = generateNextId(prefix);

        roleuser.setId(nextId);
        roleuser.setRole(subAdminRole);
        if (!roleuser.getPassword().startsWith("$2a$")) {
            roleuser.setPassword(passwordEncoder.encode(roleuser.getPassword()));
        }
        return roleuserRepository.save(roleuser);
    }

    private String getRolePrefix(Role role) {
        return switch (role) {
            case DIETICIAN -> "DT";
            case PHYSIO -> "PH";
            case DOCTOR -> "DR";
            case LAB -> "LB";
            case PHLEBOTOMIST -> "PB";
            default -> "SA";  // Default Sub-Admin prefix
        };
    }

    private String generateNextId(String prefix) {
        String lastId = roleuserRepository.findLastIdByPrefix(prefix + "%");  // Fetch the latest ID
        int nextNumber = (lastId != null) ? Integer.parseInt(lastId.substring(2)) + 1 : 1;
        return String.format("%s%03d", prefix, nextNumber);
    }

    public RoleUser createStaff(RoleUser roleuser, String subAdminId) {
        RoleUser subAdmin = roleuserRepository.findById(subAdminId)
                .orElseThrow(() -> new IllegalArgumentException("Sub-Admin not found"));

        if (!isValidSubAdminRole(subAdmin.getRole())) {
            throw new IllegalArgumentException("Assigned user is not a valid Sub-Admin.");
        }

        // Generate a staff ID with the prefix of the Sub-Admin's role
        String prefix = "ST"; // Generic prefix for staff
        String nextId = generateNextStaffId(subAdmin.getId());

        roleuser.setId(nextId);
        roleuser.setRole(Role.STAFF);
        roleuser.setManagedBy(subAdmin); // Assign staff under the Sub-Admin

        if (!roleuser.getPassword().startsWith("$2a$")) {
            roleuser.setPassword(passwordEncoder.encode(roleuser.getPassword()));
        }

        return roleuserRepository.save(roleuser);
    }

    private String generateNextStaffId(String subAdminId) {
        String lastStaffId = roleuserRepository.findLastIdByPrefix(subAdminId + "ST%");
        int nextNumber = (lastStaffId != null) ? Integer.parseInt(lastStaffId.substring(subAdminId.length() + 2)) + 1 : 1;
        return String.format("%sST%03d", subAdminId, nextNumber);
    }


    public void removeStaff(String staffId) {
        if (!roleuserRepository.existsById(staffId)) {
            throw new IllegalArgumentException("Staff not found");
        }
        roleuserRepository.deleteById(staffId);
    }

    public void removeSubAdmin(String subAdminId) {
        RoleUser subAdmin = roleuserRepository.findById(subAdminId)
                .orElseThrow(() -> new IllegalArgumentException("Sub-Admin not found"));

        if (!isValidSubAdminRole(subAdmin.getRole())) {
            throw new IllegalArgumentException("User is not a Sub-Admin.");
        }

        // Find all staff members under this Sub-Admin
        List<RoleUser> staffUnderSubAdmin = roleuserRepository.findBySubAdmin(subAdmin);

        // Set their `managed_by` field to NULL before deletion
        for (RoleUser staff : staffUnderSubAdmin) {
            staff.setManagedBy(null);  // Assuming managed_by is mapped as "managedBy" in your entity
            roleuserRepository.save(staff);
        }

        // Now delete all staff members under this Sub-Admin
        roleuserRepository.deleteAll(staffUnderSubAdmin);

        // Finally, delete the Sub-Admin
        roleuserRepository.delete(subAdmin);
    }

    private boolean isValidSubAdminRole(Role role) {
        return role == Role.SUB_ADMIN || role == Role.DOCTOR || role == Role.PHLEBOTOMIST ||
                role == Role.DIETICIAN || role == Role.PHYSIO || role == Role.LAB;
    }

    public List<RoleUser> getAllUsers() {
        return roleuserRepository.findAll();
    }
}
