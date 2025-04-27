package com.backend.CrimsonCompass.service;

import java.util.Optional;

import com.backend.CrimsonCompass.dto.UserSyncRequest;
import com.backend.CrimsonCompass.model.User;

public interface IUserService {
    User registerUser(User user);
    Optional<User> loginUser(String email, String password);
    Optional<User> getUserByEmail(String email);

    void syncOAuthUser(UserSyncRequest request);
}

