package com.example.trainticketbooking.repository.entity.booking;

import com.example.trainticketbooking.repository.entity.audit.Audited;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = false)
@Getter
@Setter
@Entity
@Table(name = "trains")
public class Train extends Audited {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private Boolean active;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(
            name = "train_station",
            joinColumns = @JoinColumn(name = "train_id"),
            inverseJoinColumns = @JoinColumn(name = "station_id"))
    private List<Station> stations;

    @OneToMany(mappedBy = "train", cascade = CascadeType.MERGE)
    private Set<TrainSection> trainSections;


}
