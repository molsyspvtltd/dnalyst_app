package com.example.app_server.BookingDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "dietician_bookings")
public class DieticianBooking extends BaseBooking {
    // Add Dietician-specific fields if needed
}
