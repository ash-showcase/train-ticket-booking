package com.example.trainticketbooking.repository;

import com.example.trainticketbooking.repository.entity.booking.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
