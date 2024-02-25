package com.example.trainticketbooking.repository.entity.booking;

import com.example.trainticketbooking.repository.entity.audit.Audited;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sections", uniqueConstraints = { @UniqueConstraint(columnNames = { "name", "train_id" }) })
public class TrainSection extends Audited {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "train_id")
    private Train train;

    @OneToMany(mappedBy = "trainSection", cascade = CascadeType.MERGE)
    private Set<Seat> seats;
}
