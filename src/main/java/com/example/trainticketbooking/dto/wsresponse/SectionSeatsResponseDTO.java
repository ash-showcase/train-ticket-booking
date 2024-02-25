package com.example.trainticketbooking.dto.wsresponse;

import com.example.trainticketbooking.dto.SeatDTO;
import com.example.trainticketbooking.dto.UserDTO;
import lombok.Data;
import org.graalvm.collections.Pair;

import java.util.List;

@Data
public class SectionSeatsResponseDTO {

    private List<Pair<SeatDTO, UserDTO>> userSeats;
}
