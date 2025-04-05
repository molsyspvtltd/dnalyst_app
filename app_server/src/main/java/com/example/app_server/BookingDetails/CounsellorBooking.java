package com.example.app_server.BookingDetails;

import com.example.app_server.UserAccountCreation.User;
import jakarta.persistence.*;

@Entity
@Table(name = "counsellor_bookings")
public class CounsellorBooking extends BaseBooking {


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Getters and Setters
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

