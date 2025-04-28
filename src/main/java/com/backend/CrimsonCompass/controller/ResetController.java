package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.service.PasswordResetService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class ResetController {

    private final PasswordResetService passwordResetService;

    @PostMapping("/request-reset")
    public ResponseEntity<String> requestReset(@RequestBody Map<String, String> body) throws Exception {
        passwordResetService.initiateReset(body.get("email"));
        return ResponseEntity.ok("Reset link sent");
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody Map<String, String> body) {
        passwordResetService.resetPassword(body.get("token"), body.get("newPassword"));
        return ResponseEntity.ok("Password has been reset successfully");
    }
}

