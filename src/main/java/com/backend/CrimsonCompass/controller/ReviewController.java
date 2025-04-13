package com.backend.CrimsonCompass.controller;

import com.backend.CrimsonCompass.dto.ReviewRequestDTO;
import com.backend.CrimsonCompass.dto.ReviewResponseDTO;
import com.backend.CrimsonCompass.service.IReviewService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    private final IReviewService reviewService;

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Autowired
    public ReviewController(IReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public void addReview(@RequestBody ReviewRequestDTO reviewRequestDTO, HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(jwtSecret.getBytes())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            Integer userId = (Integer) claims.get("userId");
            reviewRequestDTO.setUserId(Long.valueOf(userId));
        }
        reviewService.addReview(reviewRequestDTO);
    }

    @GetMapping("/{entityType}/{entityId}")
    public List<ReviewResponseDTO> getReviewsByEntity(
            @PathVariable String entityType,
            @PathVariable Long entityId) {
        return reviewService.getReviewsByEntity(entityId, entityType);
    }
}
