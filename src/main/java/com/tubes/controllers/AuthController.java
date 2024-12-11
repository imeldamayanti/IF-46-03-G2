// package com.tubes.controllers;

// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.web.bind.annotation.*;
// import com.tubes.utils.jwtUtils;

// @RestController
// @RequestMapping("/api/auth")
// public class AuthController {

//     private final AuthenticationManager authenticationManager;
//     private final jwtUtils jwtUtil;

//     public AuthController(AuthenticationManager authenticationManager, jwtUtils jwtUtil) {
//         this.authenticationManager = authenticationManager;
//         this.jwtUtil = jwtUtil;
//     }

//     @PostMapping("/login")
//     public String login(@RequestBody AuthRequest request) {
// 		System.err.println(request);
//         authenticationManager.authenticate(
//             new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
//         );
//         return jwtUtil.generateToken(request.getUsername());
//     }
// }

// class AuthRequest {
//     private String username;
//     private String password;

//     public String getUsername() {
//         return username;
//     }

//     public void setUsername(String username) {
//         this.username = username;
//     }

//     public String getPassword() {
//         return password;
//     }

//     public void setPassword(String password) {
//         this.password = password;
//     }
// }
