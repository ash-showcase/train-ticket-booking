package com.example.trainticketbooking.strategy.impl;

import com.example.trainticketbooking.repository.SeatRepository;
import com.example.trainticketbooking.repository.entity.booking.Seat;
import com.example.trainticketbooking.strategy.SeatAllocationStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class RandomSeatAllocationStrategy implements SeatAllocationStrategy {

    private final SeatRepository seatRepository;

    @Autowired
    public RandomSeatAllocationStrategy(SeatRepository seatRepository) {
        this.seatRepository = seatRepository;
    }

    @Override
    public Optional<Seat> allocate(Long sectionId) {
        Set<Seat> seats = seatRepository.findVacantSeatsBySection(sectionId);
        if(seats.isEmpty()){
            return Optional.empty();
        }
        return seats.stream().findAny();
    }
}
