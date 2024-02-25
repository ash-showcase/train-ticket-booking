package com.example.trainticketbooking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class TrainDTO {

    private Long id;
    private String name;

}
