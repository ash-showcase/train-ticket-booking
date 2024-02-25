package com.example.trainticketbooking.controller;

import com.example.trainticketbooking.dto.wsrequest.BookingRequestDTO;
import com.example.trainticketbooking.dto.wsresponse.BookingResponseDTO;
import com.example.trainticketbooking.service.BookingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/booking")
public class TrainTicketBookingController {

    @Autowired
    private BookingService bookingService;

    @PostMapping(path = "/")
    @ResponseBody
    public BookingResponseDTO createBooking(@RequestBody @Valid BookingRequestDTO request) {
        return bookingService.createBooking(request);
    }

    @GetMapping(path = "/{bookingId}")
    @ResponseBody
    public BookingResponseDTO viewBookings(@PathVariable Long bookingId) {
        return bookingService.viewBooking(bookingId);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> handleException(final Exception e, final HttpServletRequest request){
        log.error("Error handling request", e);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error handling request: " +  e.getMessage());
    }

}
