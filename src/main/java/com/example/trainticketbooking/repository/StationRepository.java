package com.example.trainticketbooking.repository;

import com.example.trainticketbooking.repository.entity.booking.Station;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StationRepository extends JpaRepository<Station, Long> {
}
