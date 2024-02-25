package com.example.trainticketbooking.repository;

import com.example.trainticketbooking.repository.entity.booking.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    @Query("SELECT ticket FROM Ticket ticket JOIN ticket.seat seat JOIN seat.trainSection section WHERE section.id=:sectionId")
    List<Ticket> findTicketsBySection(@Param("sectionId") Long sectionId);

}
