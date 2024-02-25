package com.example.trainticketbooking.dto.wsresponse;

import com.example.trainticketbooking.dto.TicketDTO;
import lombok.Data;

@Data
public class BookingResponseDTO {

    private TicketDTO ticket;
    private Status status;

    public enum Status {
        CONFIRMED, PENDING, CANCELLED
    }
}
