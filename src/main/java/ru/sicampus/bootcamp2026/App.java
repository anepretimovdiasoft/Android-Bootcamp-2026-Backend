package ru.sicampus.bootcamp2026;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import ru.sicampus.bootcamp2026.repository.UserRepository;

@SpringBootApplication
public class App {

    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(UserRepository userRepository) {
        return args -> {
            System.out.println("Checking users in DB:");
            userRepository.findAll().forEach(user -> {
                    System.out.println("User: " + user.getEmail() + " | Name: " + user.getFullName());
                });
        };
    }
}
