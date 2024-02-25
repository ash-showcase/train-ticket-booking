package com.example.trainticketbooking.integration;

import com.example.trainticketbooking.dto.UserDTO;
import com.example.trainticketbooking.dto.wsrequest.BookingRequestDTO;
import com.example.trainticketbooking.dto.wsresponse.BookingResponseDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookingServiceTest {

    @LocalServerPort
    private int port;

//    @Bean
//    public RestTemplate restTemplate() {
//        final RestTemplate restTemplate = new RestTemplate();
//
//        List<HttpMessageConverter<?>> messageConverters = new ArrayList<>();
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        converter.setSupportedMediaTypes(Collections.singletonList(MediaType.ALL));
//        messageConverters.add(converter);
//        restTemplate.setMessageConverters(messageConverters);
//
//        return restTemplate;
//    }

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void greetingShouldReturnDefaultMessage() {
        BookingRequestDTO requestDTO = new BookingRequestDTO();
        requestDTO.setFromStationCode(1L);
        requestDTO.setToStationCode(2L);
        requestDTO.setSectionId(1L);
        requestDTO.setTrainId(1L);
        requestDTO.setUser(new UserDTO("Ashish", "Grover", "ashish.grover.tgd@gmail.com", 1L));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<BookingRequestDTO> request = new HttpEntity<>(requestDTO, headers);

        ResponseEntity<BookingResponseDTO> responseDTO = restTemplate.postForEntity("http://localhost:" + port + "/train-ticket/booking/", request, BookingResponseDTO.class);

        Assertions.assertEquals(responseDTO.getStatusCode(), HttpStatus.OK);
        Assertions.assertNotNull(responseDTO.getBody().getTicket());
        Assertions.assertNotNull(responseDTO.getBody().getTicket().getSeat());
    }

}
