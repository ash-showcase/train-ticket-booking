package com.example.trainticketbooking.repository.entity.booking;


import com.example.trainticketbooking.repository.entity.audit.Audited;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "stations", uniqueConstraints = { @UniqueConstraint(columnNames = { "name"}) })
public class Station extends Audited {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @ManyToMany(mappedBy = "stations")
    private List<Train> trains;
}
