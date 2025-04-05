package com.example.app_server.dietphysio.dto;

import com.example.app_server.UserAccountCreation.User;

public class CreateChartRequest {
    private User user;
    private int chartNumber;
    private String startDate;
    private String endDate;

    // Getters and setters
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
