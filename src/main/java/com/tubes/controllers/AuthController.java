package com.tubes.controllers;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import com.tubes.service.UserService;
import com.tubes.utils.jwtUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> request) throws Exception {

		String firstName = request.get("firstName");
		String lastName = request.get("lastName");
		String email = request.get("email");
		String username = request.get("username");
		String password = request.get("password");

		if (userService.checkEmailExisting(email)) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "User with email " + email + " already exists."));
        }

		if (userService.checkUsernameExisting(email)) {
            return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
                .body(Map.of("message", "User with username " + username + " already exists."));
        }

		try {
			userService.saveReader(firstName, lastName, email, username, password);

			return ResponseEntity.ok(Map.of("message", "Signup successful"));

        } catch (Exception e) {
            return ResponseEntity
				.status(HttpStatus.BAD_REQUEST)
				.contentType(MediaType.APPLICATION_JSON)
				.body(Map.of("message", e.getMessage()));
        }
    }
}