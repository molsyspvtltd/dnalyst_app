package com.example.app_server.BookingDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/doctor-bookings")
public class DoctorBookingController {

    @Autowired
    private DoctorBookingRepository doctorBookingRepository;

    @Autowired
    private BookingService bookingService;

    // CREATE a new Doctor booking
    @PostMapping
    public ResponseEntity<ResponseEntity<String>> createBooking(@RequestBody DoctorBookingRequest request) {
        ResponseEntity<String> booking = bookingService.bookDoctor(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(booking);
    }

    // READ all bookings for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DoctorBooking>> getBookingsByUserId(@PathVariable String userId) {
        List<DoctorBooking> bookings = doctorBookingRepository.findByMrnId(userId);
        return ResponseEntity.ok(bookings);
    }

    // READ the latest booking time for a specific user
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<LocalDateTime> getLastBookingTimeByUserId(@PathVariable String mrnId) {
        LocalDateTime lastBookingTime = doctorBookingRepository.findLastBookingTimeByMrnId(mrnId);
        return lastBookingTime != null ? ResponseEntity.ok(lastBookingTime) : ResponseEntity.notFound().build();
    }

    // UPDATE the latest Doctor booking for a specific user
    @PutMapping("/user/{userId}")
    public ResponseEntity<DoctorBooking> updateBookingByUserId(@PathVariable String mrnId,
                                                               @RequestBody DoctorBooking updatedBooking) {
        return doctorBookingRepository.findFirstByMrnIdOrderByBookingTimeDesc(mrnId)
                .map(existingBooking -> {
                    existingBooking.setBookingTime(updatedBooking.getBookingTime());
                    existingBooking.setLocation(updatedBooking.getLocation());
                    doctorBookingRepository.save(existingBooking);
                    return ResponseEntity.ok(existingBooking);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE the latest Doctor booking for a specific user
    @DeleteMapping("/user/{userId}/latest")
    public ResponseEntity<Object> deleteLatestBookingByUserId(@PathVariable String mrnId) {
        return doctorBookingRepository.findFirstByMrnIdOrderByBookingTimeDesc(mrnId)
                .map(booking -> {
                    doctorBookingRepository.delete(booking);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
