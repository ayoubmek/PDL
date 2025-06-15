package com.pdl.pdl;

import com.pdl.pdl.entity.AppUser;
import com.pdl.pdl.entity.Role;
import com.pdl.pdl.repository.RoleRepository;
import com.pdl.pdl.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class AuthTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthTestApplication.class, args);
	}

	 @Bean
	 public CommandLineRunner run(RoleRepository roleRepo, UserRepository userRepo, PasswordEncoder passwordEncoder) {
	      return args -> {
	        Role adminRole = roleRepo.findByAuthority("ADMIN").orElse(null);

	         if (adminRole == null) {
	                adminRole = new Role("ADMIN");
	                adminRole = roleRepo.save(adminRole);
	            }
				 AppUser adminUser = userRepo.findByUsername("admin").orElse(null);
	         if (adminUser == null) {
	                Set<Role> roles = new HashSet<>();
	                roles.add(adminRole);
	                AppUser admin = new AppUser();
	                admin.setUsername("admin");
	                admin.setPassword(passwordEncoder.encode("adminPassword"));
	                admin.setBirthdate(new Date());
	                admin.setAuthorities(roles);
	                userRepo.save(admin);
	            }
	         Role userRole = roleRepo.findByAuthority("USER").orElse(null);
	         if (userRole == null) {
	             userRole = new Role("USER");
	             userRole = roleRepo.save(userRole);
	         }

	        };
	    }


}
