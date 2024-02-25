package com.example.trainticketbooking.strategy.impl;

import com.example.trainticketbooking.repository.SeatRepository;
import com.example.trainticketbooking.repository.entity.booking.Seat;
import com.example.trainticketbooking.strategy.SeatAllocationStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public class NextAvailableSeatAllocationStrategy  implements SeatAllocationStrategy {
    private final SeatRepository seatRepository;

    @Autowired
    public NextAvailableSeatAllocationStrategy(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }
    @Override
    public Optional<Seat> allocate(Long sectionId) {
        Set<Seat> seats = seatRepository.findVacantSeatsBySection(sectionId);
        if(seats.isEmpty()){
            return Optional.empty();
        }
        return seats.stream().sorted(Comparator.comparing(Seat::getRowNum).thenComparing(Seat::getSeat)).findFirst();
    }
}
