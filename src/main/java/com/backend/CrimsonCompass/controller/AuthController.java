package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.model.User;
import com.backend.CrimsonCompass.service.UserService;
import com.backend.CrimsonCompass.util.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtils jwtUtils;
    private final UserService userService;

    @Autowired
    public AuthController(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(HttpServletRequest request) {
        try {
            String authHeader = request.getHeader("Authorization");
            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            String token = authHeader.substring(7);
            String authId = jwtUtils.validateTokenAndGetAuthId(token);

            if (authId == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Get user details by authId
            Optional<User> user = userService.getUserByAuthId(authId);
            if (user == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }

            // Return user data
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
