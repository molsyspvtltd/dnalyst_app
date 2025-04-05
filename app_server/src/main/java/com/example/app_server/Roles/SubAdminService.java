package com.example.app_server.Roles;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubAdminService {
    private final RoleUserRepository roleuserRepository;
    private final PasswordEncoder passwordEncoder;

    public SubAdminService(RoleUserRepository roleuserRepository, PasswordEncoder passwordEncoder) {
        this.roleuserRepository = roleuserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private String getCurrentUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            throw new RuntimeException("User not authenticated: " + principal);
        }
    }

    public String changePassword(String email, PasswordChangeRequest request) {
        RoleUser subAdmin = roleuserRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Sub-Admin not found"));

        // Check if old password matches
        if (!passwordEncoder.matches(request.getOldPassword(), subAdmin.getPassword())) {
            return "Error: Old password is incorrect!";
        }

        // Encode and update the new password
        subAdmin.setPassword(passwordEncoder.encode(request.getNewPassword()));
        roleuserRepository.save(subAdmin);

        return "Password changed successfully!";
    }

    public List<RoleUser> getAllStaff() {
        String subAdminEmail = getCurrentUserEmail();
        RoleUser subAdmin = roleuserRepository.findByEmail(subAdminEmail)
                .orElseThrow(() -> new RuntimeException("Sub-Admin not found"));

        System.out.println("Sub-Admin ID: " + subAdmin.getId());
        System.out.println("Sub-Admin Email: " + subAdmin.getEmail());

        List<RoleUser> staffList = roleuserRepository.findByManagedBy(subAdmin);
        System.out.println("Staff Count: " + staffList.size());

        return staffList;
    }


    public RoleUser createStaff(RoleUser roleuser) {
        String subAdminEmail = getCurrentUserEmail();
        RoleUser subAdmin = roleuserRepository.findByEmail(subAdminEmail)
                .orElseThrow(() -> new RuntimeException("Sub-Admin not found"));

        // Generate unique Staff ID
        String nextStaffId = generateNextStaffId(subAdmin.getId());

        roleuser.setId(nextStaffId); // Set generated staff ID
        roleuser.setManagedBy(subAdmin);
        roleuser.setRole(Role.STAFF);
        roleuser.setPassword(passwordEncoder.encode(roleuser.getPassword()));

        return roleuserRepository.save(roleuser);
    }

    // Helper function to generate next Staff ID
    private String generateNextStaffId(String subAdminId) {
        String lastStaffId = roleuserRepository.findLastIdByPrefix(subAdminId + "ST%"); // Fetch latest staff ID under sub-admin
        int nextNumber = (lastStaffId != null) ? Integer.parseInt(lastStaffId.substring(subAdminId.length() + 2)) + 1 : 1;
        return String.format("%sST%03d", subAdminId, nextNumber);
    }

    public String deleteStaff(String staffId) {
        String subAdminEmail = getCurrentUserEmail();
        RoleUser subAdmin = roleuserRepository.findByEmail(subAdminEmail)
                .orElseThrow(() -> new RuntimeException("Sub-Admin not found"));

        RoleUser staff = roleuserRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        // Ensure the staff belongs to the current sub-admin
        if (!staff.getManagedBy().equals(subAdmin)) {
            return "Error: You can only remove your own staff!";
        }

        roleuserRepository.delete(staff);
        return "Staff removed successfully!";
    }


}
