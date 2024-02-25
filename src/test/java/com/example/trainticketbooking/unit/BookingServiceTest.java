package com.example.trainticketbooking.unit;

import com.example.trainticketbooking.dto.UserDTO;
import com.example.trainticketbooking.dto.wsrequest.BookingRequestDTO;
import com.example.trainticketbooking.dto.wsresponse.BookingResponseDTO;
import com.example.trainticketbooking.repository.*;
import com.example.trainticketbooking.repository.entity.booking.*;
import com.example.trainticketbooking.service.impl.BookingServiceImpl;
import com.example.trainticketbooking.strategy.SeatAllocationStrategy;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookingServiceTest {

    private BookingServiceImpl bookingService;

    @Mock
    private TrainRepository trainRepository;
    @Mock
    private StationRepository stationRepository;
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private SeatAllocationStrategy seatAllocationStrategy;
    @Mock
    private UserRepository userRepository;
    @Mock
    private SeatRepository seatRepository;

    @BeforeEach
    void initUseCase() {
        bookingService = new BookingServiceImpl(trainRepository, stationRepository, ticketRepository, seatAllocationStrategy, userRepository, seatRepository);
    }

    @Test
    void createdBookingHasSeatAllocated() {
        BookingRequestDTO requestDTO = new BookingRequestDTO();
        requestDTO.setFromStationCode(1L);
        requestDTO.setToStationCode(2L);
        requestDTO.setSectionId(1L);
        requestDTO.setTrainId(1L);
        requestDTO.setUser(new UserDTO("Ashish", "Grover", "ashish.grover.tgd@gmail.com", 1L));

        when(userRepository.findById(anyLong())).then(invocation -> {
            User user = new User();
            user.setFirstName("Ashish");
            user.setLastName("Grover");
            user.setEmailId("ashish.grover.tgd@gmail.com");
            return Optional.of(user);
        });
        when(stationRepository.findById(1L)).then(invocation -> {
            Station station = new Station();
            station.setId(1L);
            station.setName("London");
            return Optional.of(station);
        });
        when(stationRepository.findById(2L)).then(invocation -> {
            Station station = new Station();
            station.setId(2L);
            station.setName("Paris");
            return Optional.of(station);
        });
        Seat seat = new Seat();
        seat.setPrice(40D);
        seat.setRowNum(3);
        seat.setSeat("F");
        seat.setTrainSection(new TrainSection());
        seat.getTrainSection().setId(1L);
        seat.getTrainSection().setTrain(new Train());
        seat.getTrainSection().getTrain().setId(1L);
        when(seatAllocationStrategy.allocate(anyLong())).thenReturn(Optional.of(seat));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(null);
        when(seatRepository.save(any(Seat.class))).thenReturn(null);

        BookingResponseDTO responseDTO = bookingService.createBooking(requestDTO);

        Assertions.assertEquals(responseDTO.getTicket().getSeat().getRow(), 3);
        Assertions.assertEquals(responseDTO.getTicket().getSeat().getNum(), "F");
    }

    @Test
    void createdBookingExceptionWhenNoSeatAvailable() {
        BookingRequestDTO requestDTO = new BookingRequestDTO();
        requestDTO.setFromStationCode(1L);
        requestDTO.setToStationCode(2L);
        requestDTO.setSectionId(1L);
        requestDTO.setTrainId(1L);
        requestDTO.setUser(new UserDTO("Ashish", "Grover", "ashish.grover.tgd@gmail.com", 1L));

        when(userRepository.findById(anyLong())).then(invocation -> {
            User user = new User();
            user.setFirstName("Ashish");
            user.setLastName("Grover");
            user.setEmailId("ashish.grover.tgd@gmail.com");
            return Optional.of(user);
        });
        when(stationRepository.findById(1L)).then(invocation -> {
            Station station = new Station();
            station.setId(1L);
            station.setName("London");
            return Optional.of(station);
        });
        when(stationRepository.findById(2L)).then(invocation -> {
            Station station = new Station();
            station.setId(2L);
            station.setName("Paris");
            return Optional.of(station);
        });
        when(seatAllocationStrategy.allocate(anyLong())).thenReturn(Optional.empty());

        Assertions.assertThrowsExactly(IllegalArgumentException.class, () -> bookingService.createBooking(requestDTO));

    }
}
