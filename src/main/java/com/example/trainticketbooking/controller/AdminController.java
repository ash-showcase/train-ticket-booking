package com.example.trainticketbooking.controller;

import com.example.trainticketbooking.dto.SeatDTO;
import com.example.trainticketbooking.dto.wsresponse.BookingResponseDTO;
import com.example.trainticketbooking.dto.wsresponse.SectionSeatsResponseDTO;
import com.example.trainticketbooking.service.BookingAdminService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private BookingAdminService bookingAdminService;

    @GetMapping(path = "/section/{sectionId}")
    @ResponseBody
    public SectionSeatsResponseDTO viewUsersAllocatedSeats(@PathVariable Long sectionId) {
        return bookingAdminService.viewBookingsBySection(sectionId);
    }

    @DeleteMapping(path = "/booking/{bookingId}")
    public void deleteBooking(@PathVariable Long bookingId) {
        bookingAdminService.deleteBooking(bookingId);
    }

    @PostMapping(path = "/reassign/{bookingId}")
    @ResponseBody
    public BookingResponseDTO changeSeat(@PathVariable Long bookingId, @RequestBody SeatDTO seat) {
        return bookingAdminService.reassignSeat(bookingId, seat.getRow(), seat.getNum());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(final Exception e, final HttpServletRequest request){
        log.error("Error handling request", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error handling request: " +  e.getMessage());
    }

}
