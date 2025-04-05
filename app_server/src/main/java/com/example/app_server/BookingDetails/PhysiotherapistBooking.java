package com.example.app_server.BookingDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "physiotherapist_bookings")
public class PhysiotherapistBooking extends BaseBooking {
    // Add Physiotherapist-specific fields if needed
}

