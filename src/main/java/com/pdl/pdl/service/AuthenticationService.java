package com.pdl.pdl.service;


import com.pdl.pdl.ResponseHandlers.AuthenticationRequest;
import com.pdl.pdl.ResponseHandlers.AuthenticationResponse;
import com.pdl.pdl.ResponseHandlers.RegisterRequest;
import com.pdl.pdl.entity.AppUser;
import com.pdl.pdl.entity.Role;
import com.pdl.pdl.repository.RoleRepository;
import com.pdl.pdl.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@Transactional
public class AuthenticationService {

	   @Autowired
	   private UserRepository userRepo;

	   @Autowired
	   private RoleRepository roleRepo;

	   @Autowired
	   private PasswordEncoder passwordEncoder;

	   @Autowired
	   private AuthenticationManager authenticationManager;

	   @Autowired
	   private JwtService jwtService;


	   public AuthenticationResponse registerUser(RegisterRequest request){

		     String encodedPassword = passwordEncoder.encode(request.getPassword());
		     Role userRole = roleRepo.findByAuthority("USER")
                     .orElseThrow(() -> new RuntimeException("Default role not found"));

		     Set<Role> authorities = new HashSet<>();
		     authorities.add(userRole);
		     AppUser user = new AppUser(
			    		request.getUsername(),
			    		encodedPassword,
			    		request.getBirthdate(),
			    		authorities);
		     userRepo.save(user);

		     var jwtToken = jwtService.generateToken(user);
		     AuthenticationResponse authResp = new AuthenticationResponse(jwtToken);
		     return authResp;
		}


	   public AuthenticationResponse authenticateUser(AuthenticationRequest request) {
		   authenticationManager.authenticate(
		     new UsernamePasswordAuthenticationToken(
		       request.getUsername(),
		       request.getPassword()
		     )
		   );
		   AppUser user = userRepo.findByUsername(request.getUsername())
				   .orElseThrow(() -> new RuntimeException("Username Not Found"));
		     var jwtToken = jwtService.generateToken(user);
		     AuthenticationResponse authResp = new AuthenticationResponse(jwtToken);
		     return authResp;
		 }

}

