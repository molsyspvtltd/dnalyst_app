package com.example.app_server.BookingDetails;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface PhlebotomistBookingRepository extends JpaRepository<PhlebotomistBooking, String> {

    // Find all bookings for a specific user's MRN ID
    @Query("SELECT b FROM PhlebotomistBooking b WHERE b.user.mrnId = :mrnId")
    List<PhlebotomistBooking> findByMrnId(@Param("mrnId") String mrnId);

    // Find the last booking time for a specific user's MRN ID
    @Query("SELECT b.bookingTime FROM PhlebotomistBooking b WHERE b.user.mrnId = :mrnId ORDER BY b.bookingTime DESC")
    LocalDateTime findLastBookingTimeByMrnId(@Param("mrnId") String mrnId);

    // Find the latest booking for a specific user's MRN ID
    @Query("SELECT b FROM PhlebotomistBooking b WHERE b.user.mrnId = :mrnId ORDER BY b.bookingTime DESC")
    Optional<PhlebotomistBooking> findFirstByMrnIdOrderByBookingTimeDesc(@Param("mrnId") String mrnId);

    // Check if a user has any bookings by their MRN ID
    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN TRUE ELSE FALSE END FROM PhlebotomistBooking b WHERE b.user.mrnId = :mrnId")
    boolean existsByMrnId(@Param("mrnId") String mrnId);
}

