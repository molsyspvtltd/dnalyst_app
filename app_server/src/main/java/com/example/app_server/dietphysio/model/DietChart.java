package com.example.app_server.dietphysio.model;


import com.example.app_server.UserAccountCreation.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class DietChart {

    // Getters and Setters
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    private  User user;

    @Column(unique = true)
    private int chartNumber;

    @OneToMany(mappedBy = "dietChart", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<DietPlan> dietPlan;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getChartNumber() {
        return chartNumber;
    }

    public void setChartNumber(int chartNumber) {
        this.chartNumber = chartNumber;
    }

    public List<DietPlan> getDietPlan() {
        return dietPlan;
    }

    public void setDietPlan(List<DietPlan> dietPlan) {
        this.dietPlan = dietPlan;
    }
}
