package com.example.trainticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class SeatDTO {
    private Integer row;
    private String num;
}
