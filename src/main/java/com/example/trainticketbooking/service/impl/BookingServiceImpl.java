package com.example.trainticketbooking.service.impl;

import com.example.trainticketbooking.dto.*;
import com.example.trainticketbooking.dto.wsrequest.BookingRequestDTO;
import com.example.trainticketbooking.dto.wsresponse.BookingResponseDTO;
import com.example.trainticketbooking.repository.*;
import com.example.trainticketbooking.repository.entity.booking.*;
import com.example.trainticketbooking.service.BookingService;
import com.example.trainticketbooking.strategy.SeatAllocationStrategy;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.function.Supplier;

@Service
public class BookingServiceImpl implements BookingService {

    private final TrainRepository trainRepository;
    private final StationRepository stationRepository;
    private final TicketRepository ticketRepository;
    private final SeatAllocationStrategy seatAllocationStrategy;
    private final UserRepository userRepository;
    private final SeatRepository seatRepository;

    @Autowired
    public BookingServiceImpl(TrainRepository trainRepository, StationRepository stationRepository, TicketRepository ticketRepository, SeatAllocationStrategy seatAllocationStrategy, UserRepository userRepository, SeatRepository seatRepository) {
        this.trainRepository = trainRepository;
        this.stationRepository = stationRepository;
        this.ticketRepository = ticketRepository;
        this.seatAllocationStrategy = seatAllocationStrategy;
        this.userRepository = userRepository;
        this.seatRepository = seatRepository;
    }

    @Override
    @Transactional
    public BookingResponseDTO createBooking(BookingRequestDTO request) {
        Ticket ticket = new Ticket();

        Supplier<User> userCreator = () -> {
            User newUser = new User();
            newUser.setEmailId(request.getUser().getEmailId());
            newUser.setFirstName(request.getUser().getFirstName());
            newUser.setLastName(request.getUser().getLastName());
            userRepository.save(newUser);
            return newUser;
        };

        User user;
        if(request.getUser().getUid()!=null) {
            Optional<User> userOptional = userRepository.findById(request.getUser().getUid());
            user = userOptional.orElseGet(userCreator);
        } else {
            user = userCreator.get();
        }

        ticket.setUser(user);

        Optional<Station> fromStationOp = stationRepository.findById(request.getFromStationCode());
        ticket.setFromStation(
                fromStationOp.orElseThrow(() -> new IllegalArgumentException("Source Station not found"))
        );

        Optional<Station> toStationOp = stationRepository.findById(request.getToStationCode());
        ticket.setToStation(
                toStationOp.orElseThrow(() -> new IllegalArgumentException("Destination Station not found"))
        );

        ticket.setSeat(seatAllocationStrategy.allocate(request.getSectionId())
                .orElseThrow(() -> new IllegalArgumentException("No Seats available in the selected Section")));

        ticket.setStatus(Status.CONFIRMED);

        ticketRepository.save(ticket);
        ticket.getSeat().setVacant(false);
        seatRepository.save(ticket.getSeat());

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

    @Override
    public BookingResponseDTO viewBooking(Long requestId) {
        Optional<Ticket> ticketOp = ticketRepository.findById(requestId);
        Ticket ticket = ticketOp.orElseThrow(() -> new IllegalArgumentException("No Booking available with the id " + requestId));
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
