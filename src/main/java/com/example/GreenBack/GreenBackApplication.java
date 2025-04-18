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





}
