//package com.example.trainticketbooking.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.provisioning.InMemoryUserDetailsManager;
//
//@Configuration
//public class SecurityConfig {
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails normalUser = User.builder()
//                .username("first-user")
//                .password("123456")
//                .roles("USER")
//                .build();
////        UserDetails adminUser = User.builder()
////                .username("admin")
////                .password("nimda")
////                .roles("ADMIN")
////                .build();
//        return new InMemoryUserDetailsManager(normalUser);
//    }
//
//}
