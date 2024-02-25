package com.example.trainticketbooking.service;

import com.example.trainticketbooking.dto.wsresponse.BookingResponseDTO;
import com.example.trainticketbooking.dto.wsresponse.SectionSeatsResponseDTO;

public interface BookingAdminService {

    SectionSeatsResponseDTO viewBookingsBySection(Long sectionId);

    void deleteBooking(Long bookingId);

    BookingResponseDTO reassignSeat(Long bookingId, Integer rowNum, String seat);

}
