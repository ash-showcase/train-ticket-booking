package com.example.trainticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class StationDTO {

    private String name;
    private Long code;

}
