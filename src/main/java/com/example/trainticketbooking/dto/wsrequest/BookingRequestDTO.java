package com.example.trainticketbooking.dto.wsrequest;

import com.example.trainticketbooking.dto.SeatDTO;
import com.example.trainticketbooking.dto.UserDTO;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequestDTO {

    @NotNull
    private Long fromStationCode;
    @NotNull
    private Long toStationCode;
    @NotNull
    private UserDTO user;
    private SeatDTO seat;
    @NotNull
    private Long sectionId;
    @NotNull
    private Long trainId;
}
