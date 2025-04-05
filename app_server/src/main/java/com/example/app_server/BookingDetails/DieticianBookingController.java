package com.example.app_server.BookingDetails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/dietician-bookings")
public class DieticianBookingController {

    @Autowired
    private DieticianBookingRepository dieticianBookingRepository;

    @Autowired
    private BookingService bookingService;

    // CREATE a new Dietician booking
    @PostMapping
    public ResponseEntity<ResponseEntity<String>> createBooking(@RequestBody DieticianBookingRequest request) {
        ResponseEntity<String> booking = bookingService.bookDietician(request);
        return ResponseEntity.ok(booking);
    }

    // READ all bookings for a specific user
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DieticianBooking>> getBookingsByUserId(@PathVariable String mrnId) {
        List<DieticianBooking> bookings = dieticianBookingRepository.findByMrnId(mrnId);
        return ResponseEntity.ok(bookings);
    }

    // READ the latest booking time for a specific user
    @GetMapping("/user/{userId}/latest")
    public ResponseEntity<LocalDateTime> getLastBookingTimeByUserId(@PathVariable String mrnId) {
        LocalDateTime lastBookingTime = dieticianBookingRepository.findLastBookingTimeByMrnId(mrnId);
        return lastBookingTime != null ? ResponseEntity.ok(lastBookingTime) : ResponseEntity.notFound().build();
    }

    // UPDATE the latest Dietician booking for a specific user
    @PutMapping("/user/{userId}")
    public ResponseEntity<DieticianBooking> updateBookingByUserId(@PathVariable String mrnId,
                                                                  @RequestBody DieticianBooking updatedBooking) {
        return dieticianBookingRepository.findFirstByMrnIdOrderByBookingTimeDesc(mrnId)
                .map(existingBooking -> {
                    existingBooking.setBookingTime(updatedBooking.getBookingTime());
                    existingBooking.setLocation(updatedBooking.getLocation());
                    dieticianBookingRepository.save(existingBooking);
                    return ResponseEntity.ok(existingBooking);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE the latest Dietician booking for a specific user
    @DeleteMapping("/user/{userId}/latest")
    public ResponseEntity<Object> deleteLatestBookingByUserId(@PathVariable String mrnId) {
        return dieticianBookingRepository.findFirstByMrnIdOrderByBookingTimeDesc(mrnId)
                .map(booking -> {
                    dieticianBookingRepository.delete(booking);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
