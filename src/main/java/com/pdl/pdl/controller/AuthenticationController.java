package com.pdl.pdl.controller;

import com.pdl.pdl.ResponseHandlers.AuthenticationRequest;
import com.pdl.pdl.ResponseHandlers.AuthenticationResponse;
import com.pdl.pdl.ResponseHandlers.RegisterRequest;
import com.pdl.pdl.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthenticationController {
	   @Autowired
	   private AuthenticationService authService;

	  @PostMapping("/register")
	  public ResponseEntity<AuthenticationResponse> registerUser(
			  @RequestBody RegisterRequest request
			  ) {
		  return ResponseEntity.ok(authService.registerUser(request));
	  }

	  @PostMapping("/login")
	  public ResponseEntity<AuthenticationResponse> registerUser(
			  @RequestBody AuthenticationRequest request
			  ) {
		  return ResponseEntity.ok(authService.authenticateUser(request));
	  }

}
