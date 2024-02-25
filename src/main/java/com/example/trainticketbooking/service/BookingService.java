package com.example.trainticketbooking.service;

import com.example.trainticketbooking.dto.wsrequest.BookingRequestDTO;
import com.example.trainticketbooking.dto.wsresponse.BookingResponseDTO;

public interface BookingService {

    BookingResponseDTO createBooking(BookingRequestDTO request);

    BookingResponseDTO viewBooking(Long requestId);

}
