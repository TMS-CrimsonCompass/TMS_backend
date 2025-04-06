package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.LoginRequest;
import com.backend.CrimsonCompass.dto.LoginResponse;
import com.backend.CrimsonCompass.service.UserService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.backend.CrimsonCompass.model.User;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping("/api/users")
@CrossOrigin("http://localhost:3000")
public class UserController {
    @Autowired
    private SecretKey jwtSecretKey;

    @Autowired
    private long jwtExpiration;

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        User registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(registeredUser);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        Optional<User> user = userService.loginUser(loginRequest.getEmail(), loginRequest.getPassword());

        if (user.isPresent()) {
            // Generate JWT
            String token = Jwts.builder()
                    .setSubject(user.get().getEmail())
                    .claim("userId", user.get().getUserId())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtExpiration))
                    .signWith(jwtSecretKey)
                    .compact();

            LoginResponse response = new LoginResponse();
            response.setMessage("Login successful");
            response.setData(user.get());
            response.setAccessToken(token);

            return ResponseEntity.ok(response);
        } else {
            LoginResponse response = new LoginResponse();
            response.setMessage("Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }
    }


    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        Optional<User> user = userService.getUserByEmail(email);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

