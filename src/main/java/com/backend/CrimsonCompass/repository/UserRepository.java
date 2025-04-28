package com.backend.CrimsonCompass.repository;


import com.backend.CrimsonCompass.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Optional<User> findByAuthId(String authId);
    User findByUserId(int userId);
}

