package com.example.trainticketbooking.strategy;

import com.example.trainticketbooking.repository.entity.booking.Seat;

import java.util.Optional;

public interface SeatAllocationStrategy {

    Optional<Seat> allocate(Long sectionId);

}
