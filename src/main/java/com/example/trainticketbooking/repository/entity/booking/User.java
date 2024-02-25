package com.example.trainticketbooking.repository.entity.booking;


import com.example.trainticketbooking.repository.entity.audit.Audited;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User extends Audited {

    @Id
    @GeneratedValue
    private Long id;
    private String emailId;
    private String firstName;
    private String lastName;

    @OneToMany(mappedBy = "user")
    private List<Ticket> bookings;

}
