package com.example.trainticketbooking;

import com.example.trainticketbooking.controller.AdminController;
import com.example.trainticketbooking.controller.TrainTicketBookingController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class TrainTicketBookingApplicationTests {

	@Autowired
	private AdminController controller;

	@Autowired
	private TrainTicketBookingController trainTicketBookingController;

	@Test
	void contextLoads() {
		assertThat(controller).isNotNull();
	}

}
