package com.example.trainticketbooking.repository.entity.booking;

import com.example.trainticketbooking.repository.entity.audit.Audited;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "tickets")//, uniqueConstraints = { @UniqueConstraint(columnNames = { "from_station_id", "to_station_id", "seat_id"}) })
public class Ticket extends Audited {

    @Id
    @GeneratedValue
    private Long id;
    @ManyToOne
    @JoinColumn(name = "from_station_id")
    private Station fromStation;
    @ManyToOne
    @JoinColumn(name = "to_station_id")
    private Station toStation;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToOne
    private Seat seat;
    @Enumerated(EnumType.STRING)
    private Status status;

}
