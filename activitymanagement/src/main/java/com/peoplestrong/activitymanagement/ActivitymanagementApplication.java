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
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;

import java.util.ArrayList;
import java.util.HashSet;

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

			userService.saveUser(new User(null,"aaa dharmin0","n","hirapara","hirapara0.nileshbhai@peoplestrong.com","123",new ArrayList<>(),new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null,"bbo dharmin1","n","hirapara","hirapara1.nileshbhai@peoplestrong.com","123",new ArrayList<>(),new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null,"baba dharmin2","n","hirapara","hirapara2.nileshbhai@peoplestrong.com","123",new ArrayList<>(),new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null,"aab dharmin3","n","hirapara","hirapara3.nileshbhai@peoplestrong.com","123",new ArrayList<>(),new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null,"bcab dharmin4","n","hirapara","hirapara4.nileshbhai@peoplestrong.com","123",new ArrayList<>(),new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null,"bacc dharmin5","n","hirapara","hirapara5.nileshbhai@peoplestrong.com","123",new ArrayList<>(),new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null,"aaca dharmin6","n","hirapara","hirapara6.nileshbhai@peoplestrong.com","123",new ArrayList<>(),new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null,"abab dharmin7","n","hirapara","hirapara7.nileshbhai@peoplestrong.com","123",new ArrayList<>(),new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null,"aabba dharmin8","n","hirapara","hirapara8.nileshbhai@peoplestrong.com","123",new ArrayList<>(),new HashSet<>(),new HashSet<>()));
			userService.saveUser(new User(null,"aaad dharmin9","n","hirapara","hirapara9.nileshbhai@peoplestrong.com","123",new ArrayList<>(),new HashSet<>(),new HashSet<>()));

			userService.addRoleToUser("hirapara0.nileshbhai@peoplestrong.com","ROLE_ADMIN");
			userService.addRoleToUser("hirapara0.nileshbhai@peoplestrong.com","ROLE_USER");

		};
	}

}
