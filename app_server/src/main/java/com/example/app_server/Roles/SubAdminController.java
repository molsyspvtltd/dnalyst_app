package com.example.app_server.Roles;


import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subadmin")
public class SubAdminController {

    private final SubAdminService subAdminService;

    public SubAdminController(SubAdminService subAdminService) {
        this.subAdminService = subAdminService;
    }
    @PutMapping("/change-password")
    public String changePassword(@RequestParam String email, @RequestBody PasswordChangeRequest request) {
        return subAdminService.changePassword(email, request);
    }

    @PostMapping("/create-staff")
    public RoleUser createStaff(@RequestBody RoleUser roleuser) {
        return subAdminService.createStaff(roleuser);
    }

    @GetMapping("/all-staff")
    public List<RoleUser> getAllStaff() {
        return subAdminService.getAllStaff();
    }

    @DeleteMapping("/remove-staff/{staffId}")
    public String removeStaff(@PathVariable String staffId) {
        return subAdminService.deleteStaff(staffId);
    }

}
