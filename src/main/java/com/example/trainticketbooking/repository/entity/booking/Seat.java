package com.example.trainticketbooking.repository.entity.booking;

import com.example.trainticketbooking.repository.entity.audit.Audited;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "seats", uniqueConstraints = { @UniqueConstraint(columnNames = { "rowNum", "seat", "section_id", }) })
public class Seat extends Audited {

    @Id
    @GeneratedValue
    private Long id;
    private Integer rowNum;
    private String seat;
    @ManyToOne
    @JoinColumn(name = "section_id")
    private TrainSection trainSection;
    private Boolean vacant;
    private Double price;


}
