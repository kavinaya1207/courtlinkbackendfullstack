package com.example.courtlink.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.courtlink.entity.SystemUser;
import com.example.courtlink.service.AuthService;
import com.example.courtlink.service.JwtService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final AuthService authService;

    public AuthController(JwtService jwtService,
                          AuthenticationManager authenticationManager,
                          AuthService authService) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.authService = authService;
    }
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody SystemUser user) {

        try {
            SystemUser savedUser = authService.register(user);
            return ResponseEntity.ok(savedUser);

        } catch (Exception e) {

            Map<String, String> err = new HashMap<>();

            if (e.getMessage() != null &&
                e.getMessage().toLowerCase().contains("email")) {
                err.put("message", "Email already registered");
            } else {
                err.put("message", "Registration failed: " + e.getMessage());
            }

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody SystemUser user) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            user.getEmail(),
                            user.getPassword()
                    )
            );

            String token = jwtService.generateToken(user.getEmail());

            Map<String, Object> res = new HashMap<>();
            res.put("message", "Login Successful");
            res.put("token", token);

            return ResponseEntity.ok(res);

        } catch (BadCredentialsException e) {

            Map<String, Object> res = new HashMap<>();
            res.put("message", "Invalid Email or Password");

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(res);
        }
    }
}
