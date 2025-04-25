package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.LoginRequest;
import com.backend.CrimsonCompass.dto.LoginResponse;
import com.backend.CrimsonCompass.dto.UserSyncRequest;
import com.backend.CrimsonCompass.security.JwtProperties;
import com.backend.CrimsonCompass.service.UserService;
import io.jsonwebtoken.Jwts;
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
    private final SecretKey jwtSecretKey;
    private final JwtProperties jwtProperties;
    private final UserService   userService;

    public UserController(SecretKey jwtSecretKey, JwtProperties jwtProperties, UserService userService) {
        this.jwtSecretKey = jwtSecretKey;
        this.jwtProperties = jwtProperties;
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
            long ttl = jwtProperties.getExpiration();
            String token = Jwts.builder()
                    .setSubject(user.get().getEmail())
                    .claim("userId", user.get().getUserId())
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + ttl))
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

    @PostMapping("/sync")
    public ResponseEntity<Void> syncUser(@RequestBody UserSyncRequest request) {
        userService.syncOAuthUser(request);
        return ResponseEntity.ok().build();
    }
}

