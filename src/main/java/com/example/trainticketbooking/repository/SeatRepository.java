package com.example.trainticketbooking.repository;

import com.example.trainticketbooking.repository.entity.booking.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("SELECT seats FROM Seat seats JOIN seats.trainSection sec WHERE sec.id=:secId AND seats.vacant=true " +
            "AND seats.rowNum = :rowNum AND seats.seat =:seatId")
    Optional<Seat> findByRowNumAndSeatAndSection(@Param("rowNum") Integer rowNum, @Param("seatId")String seatId, @Param("secId") Long secId);

    @Query("SELECT seat FROM Seat seat JOIN seat.trainSection sec WHERE sec.id=:secId AND seat.vacant=true")
    Set<Seat> findVacantSeatsBySection(@Param("secId") Long sectionId);

}
