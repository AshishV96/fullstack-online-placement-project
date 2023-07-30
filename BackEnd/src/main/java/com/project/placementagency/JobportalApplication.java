package com.project.placementagency;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.placementagency.model.UserDTO;
import com.project.placementagency.util.JwtUtil;

@SpringBootApplication
public class JobportalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JobportalApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public ObjectMapper gObjectMapperBean() {
		return new ObjectMapper();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	// @Override
	// public void run(String... args) throws Exception {
		
	// 	ObjectMapper mapper = new ObjectMapper();
	// 	JwtUtil util = new JwtUtil();
	// 	String userString = mapper.writeValueAsString(new UserDTO());
	// 	String token = util.createToken(new HashMap<>(), userString);
	// 	System.out.println("---------------------JWT-------------------");
	// 	System.out.println(token);

	// 	System.out.println("---------------------USER-------------------");
	// 	userString = util.extractUsername(token);
	// 	// userString = userString.replace("{", "").replace("}", "").split(",")[3];
	// 	System.out.println(userString);
	
	// }

}
