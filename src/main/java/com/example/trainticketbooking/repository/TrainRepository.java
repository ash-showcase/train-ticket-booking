package com.example.trainticketbooking.repository;

import com.example.trainticketbooking.repository.entity.booking.Train;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long> {
}
