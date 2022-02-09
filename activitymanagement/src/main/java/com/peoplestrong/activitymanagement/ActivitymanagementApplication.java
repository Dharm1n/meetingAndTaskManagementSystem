package com.peoplestrong.activitymanagement;

import com.peoplestrong.activitymanagement.models.Role;
import com.peoplestrong.activitymanagement.models.User;
import com.peoplestrong.activitymanagement.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;

@SpringBootApplication
public class ActivitymanagementApplication {

	public static void main(String[] args) {
		SpringApplication.run(ActivitymanagementApplication.class, args);
	}


	@Bean
	public PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner run(UserService userService){
		return args -> {
			userService.saveRole(new Role(null,"ROLE_USER"));
			userService.saveRole(new Role(null,"ROLE_ADMIN"));

			userService.saveUser(new User(null,"dharmin","n","hirapara","hirapara.nileshbhai@peoplestrong.com","123",new ArrayList<>()));

			userService.addRoleToUser("hirapara.nileshbhai@peoplestrong.com","ROLE_ADMIN");
			userService.addRoleToUser("hirapara.nileshbhai@peoplestrong.com","ROLE_USER");

		};
	}

}
