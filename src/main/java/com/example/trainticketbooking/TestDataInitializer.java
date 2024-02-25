package com.example.trainticketbooking;


import com.example.trainticketbooking.repository.entity.booking.Seat;
import com.example.trainticketbooking.repository.entity.booking.Station;
import com.example.trainticketbooking.repository.entity.booking.Train;
import com.example.trainticketbooking.repository.entity.booking.TrainSection;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TestDataInitializer implements ApplicationRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        Station s1 = new Station();
        s1.setName("London");

        Station s2 = new Station();
        s2.setName("Paris");

        entityManager.persist(s1);
        entityManager.persist(s2);

        Train t1 = new Train();
        t1.setName("Intercontinental");
        t1.setStations(List.of(s1, s2));

        TrainSection sec1 = new TrainSection();
        sec1.setTrain(t1);
        TrainSection sec2 = new TrainSection();
        sec2.setTrain(t1);

        for(int i=0; i<6; i++){
            Seat seat = new Seat();
            seat.setRowNum(i/3+1);
            seat.setSeat(String.valueOf((char)('A'+i%3)));
            seat.setTrainSection(sec1);
            seat.setVacant(true);
            seat.setPrice(20D);
            entityManager.persist(seat);
        }

        for(int i=0; i<6; i++){
            Seat seat = new Seat();
            seat.setRowNum(i/3+1);
            seat.setSeat(String.valueOf((char)('A'+i%3)));
            seat.setTrainSection(sec2);
            seat.setVacant(true);
            seat.setPrice(20D);
            entityManager.persist(seat);
        }

        entityManager.persist(sec1);
        entityManager.persist(sec2);
        entityManager.persist(t1);

    }
}