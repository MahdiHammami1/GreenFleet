package com.example.GreenBack;

import com.example.GreenBack.entity.Admin;
import com.example.GreenBack.entity.User;
import com.example.GreenBack.entity.Vehicle;
import com.example.GreenBack.enums.Gender;

import com.example.GreenBack.repository.AdminRepository;
import com.example.GreenBack.repository.UserRepository;
import com.example.GreenBack.repository.VehicleRepository;
import com.example.GreenBack.service.impl.UserServiceImpl;
import com.example.GreenBack.service.impl.VehicleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@SpringBootApplication
public class GreenBackApplication {
	public static void main(String[] args) {
		SpringApplication.run(GreenBackApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(VehicleRepository vehicleRepository) {
		return (args) -> {
			List<Vehicle> list = vehicleRepository.findAll();
			System.out.println(">>> Found vehicles: " + list.size());

		};
	}





}
