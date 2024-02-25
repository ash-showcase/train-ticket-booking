package com.example.trainticketbooking.service.impl;

import com.example.trainticketbooking.dto.*;
import com.example.trainticketbooking.dto.wsresponse.BookingResponseDTO;
import com.example.trainticketbooking.dto.wsresponse.SectionSeatsResponseDTO;
import com.example.trainticketbooking.repository.SeatRepository;
import com.example.trainticketbooking.repository.TicketRepository;
import com.example.trainticketbooking.repository.entity.booking.Seat;
import com.example.trainticketbooking.repository.entity.booking.Ticket;
import com.example.trainticketbooking.repository.entity.booking.User;
import com.example.trainticketbooking.service.BookingAdminService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingAdminServiceImpl implements BookingAdminService{

    private final TicketRepository ticketRepository;

    private final SeatRepository seatRepository;

    @Autowired
    public BookingAdminServiceImpl(TicketRepository ticketRepository, SeatRepository seatRepository) {
        this.ticketRepository = ticketRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    public SectionSeatsResponseDTO viewBookingsBySection(Long sectionId) {
        List<Ticket> bookings = ticketRepository.findTicketsBySection(sectionId);
        if(bookings.isEmpty()){
            throw new IllegalArgumentException("Section is empty or does not exist");
        }

        SectionSeatsResponseDTO responseDTO = new SectionSeatsResponseDTO();
        responseDTO.setUserSeats(bookings.stream().map(booking -> {
                User user = booking.getUser();
                Seat seat = booking.getSeat();
                return Pair.of(
                        SeatDTO.builder().row(seat.getRowNum()).num(seat.getSeat()).build(),
                        UserDTO.builder().firstName(user.getFirstName()).lastName(user.getLastName()).emailId(user.getEmailId()).build()
                );
            }).collect(Collectors.toList())
        );

        return responseDTO;
    }

    @Override
    public void deleteBooking(Long bookingId) {
        if(ticketRepository.existsById(bookingId)){
            ticketRepository.deleteById(bookingId);
        } else {
            throw new IllegalArgumentException("No booking found with ID: " + bookingId);
        }
    }

    @Override
    @Transactional
    public BookingResponseDTO reassignSeat(Long bookingId, Integer rowNum, String seat) {
        Optional<Ticket> ticketOp = ticketRepository.findById(bookingId);
        Ticket ticket = ticketOp.orElseThrow(() -> new IllegalArgumentException("No Booking available with the id " + bookingId));

        Seat oldSeat = ticket.getSeat();
        oldSeat.setVacant(true);
        ticket.setSeat(seatRepository.findByRowNumAndSeatAndSection(rowNum, seat, ticket.getSeat().getTrainSection().getId())
                .orElseThrow(() -> new IllegalArgumentException("No Seats available or seat not vacant for provided row number and seat number")));
        ticket.getSeat().setVacant(false);
        ticketRepository.save(ticket);

        User user = ticket.getUser();
        BookingResponseDTO response = new BookingResponseDTO();
        response.setStatus(BookingResponseDTO.Status.CONFIRMED);
        TicketDTO ticketResponse = TicketDTO.builder()
                .ticketId(ticket.getId())
                .train(TrainDTO.builder().id(ticket.getSeat().getTrainSection().getTrain().getId()).name(ticket.getSeat().getTrainSection().getTrain().getName()).build())
                .fromStation(StationDTO.builder().code(ticket.getFromStation().getId()).name(ticket.getFromStation().getName()).build())
                .toStation(StationDTO.builder().code(ticket.getToStation().getId()).name(ticket.getToStation().getName()).build())
                .user(UserDTO.builder().firstName(user.getFirstName()).lastName(user.getLastName()).emailId(user.getEmailId()).uid(user.getId()).build())
                .price(ticket.getSeat().getPrice())
                .seat(SeatDTO.builder().num(ticket.getSeat().getSeat()).row(ticket.getSeat().getRowNum()).build())
                .build();
        response.setTicket(ticketResponse);

        return response;

    }

}
