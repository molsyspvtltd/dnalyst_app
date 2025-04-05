package com.example.app_server.PrescriptionDetails;

import com.example.app_server.UserAccountCreation.User;
import jakarta.persistence.*;

@Entity
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Store file path instead of prescription details as text
    @Column(nullable = false)
    private String filePath;

    @ManyToOne
    @JoinColumn(name = "mrnId", nullable = false)
    private User user;

    // Constructors, Getters, and Setters
    public Prescription() {
    }

    public Prescription(String filePath, User user) {
        this.filePath = filePath;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
