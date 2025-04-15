package com.example.GreenBack;

import com.example.GreenBack.entity.Admin;
import com.example.GreenBack.entity.User;
import com.example.GreenBack.enums.Gender;

import com.example.GreenBack.repository.AdminRepository;
import com.example.GreenBack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class GreenBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(GreenBackApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(UserRepository userRepository) {
		return args -> {
			User user = User.builder()
					.firstname("aziz")
					.lastname("moussi")
					.email("moussi.aziz@ensi.com")
					.password("ensiisne")
					.phoneNumber("1234567890")
					.gender(Gender.Male)
					.dateOfBirth(LocalDate.of(2002, 11, 28))
					.rating(4.5f)
					.gamificationPoints(30)
					.verified(false)
					.profilePictureUrl("images/aziz.jpg")
					.build();

			userRepository.save(user);
		};
	}



}
