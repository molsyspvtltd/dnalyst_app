package com.example.app_server.BookingDetails;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "phlebotomist_bookings")
public class PhlebotomistBooking extends BaseBooking {

    @Override
    public void setGoogleMeetLink(String googleMeetLink) {
        throw new UnsupportedOperationException("Phlebotomist bookings are always offline.");
    }
}

