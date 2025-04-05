//package com.example.app_server.Roles;
//
//import jakarta.persistence.*;
//
//@Entity
//@Table(name = "staff")
//public class Staff {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private String name;
//
//    @Column(unique = true, nullable = false)
//    private String email;
//
//    @Column(nullable = false)
//    private String phone;
//
//    @Column(unique = true, nullable = false)
//    private String staffId;
//
//    @Column(nullable = false)
//    private String password;
//
//    @Enumerated(EnumType.STRING)
//    private Role role = Role.STAFF;
//
//    @ManyToOne
//    @JoinColumn(name = "sub_admin_id")
//    private SubAdmin subAdmin; // Staff belongs to a Sub-Admin
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getStaffId() {
//        return staffId;
//    }
//
//    public void setStaffId(String staffId) {
//        this.staffId = staffId;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }
//
//    public Role getRole() {
//        return role;
//    }
//
//    public void setRole(Role role) {
//        this.role = role;
//    }
//
//    public SubAdmin getSubAdmin() {
//        return subAdmin;
//    }
//
//    public void setSubAdmin(SubAdmin subAdmin) {
//        this.subAdmin = subAdmin;
//    }
//}
