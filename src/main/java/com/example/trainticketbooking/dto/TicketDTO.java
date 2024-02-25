package com.example.trainticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TicketDTO {

    private Long ticketId;
    private TrainDTO train;
    private StationDTO fromStation;
    private StationDTO toStation;
    private UserDTO user;
    private Double price;
    private SeatDTO seat;

}
