package com.example.app_server.BookingDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "doctor_bookings")
public class DoctorBooking extends BaseBooking {
    // Add Doctor-specific fields if needed
}
