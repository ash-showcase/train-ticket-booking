# Train Ticket Booking

To run this Spring Boot (Gradle) application, go to the directory from terminal and run:

`./gradlew bootRun`

A postman collection to test the APIs is included as TrainTicketBooking.postman_collection.json


I have also implemented persistence layer with in memory database. I have added some test initial data in the TestDataInitialzer class like, some trains, stations, and train sections. Train sections contain some seats which can be assigned to users. When a user creates a booking, he is assigned a seat from the section which is vacant. Seats are assigned a row and a seat number like 1A or 2C. Here 1A is a seat 'A' in row '1'.
