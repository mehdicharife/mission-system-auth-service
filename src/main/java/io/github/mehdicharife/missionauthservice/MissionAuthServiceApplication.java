package io.github.mehdicharife.missionauthservice;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import io.github.mehdicharife.missionauthservice.domain.JwtRevocation;
import io.github.mehdicharife.missionauthservice.repository.JwtRevocationRepository;

@SpringBootApplication
public class MissionAuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MissionAuthServiceApplication.class, args);
	}



}
