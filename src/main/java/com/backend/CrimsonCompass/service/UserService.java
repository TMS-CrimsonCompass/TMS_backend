package com.backend.CrimsonCompass.service;


import com.backend.CrimsonCompass.dto.UserSyncRequest;
import com.backend.CrimsonCompass.model.User;
import com.backend.CrimsonCompass.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));  // Encrypt password
        return userRepository.save(user);
    }

    public Optional<User> loginUser(String email, String password) {
        Optional<User> user = userRepository.findByEmail(email);
        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
            return user;
        }
        return Optional.empty();
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public void syncOAuthUser(UserSyncRequest request) {
        // Try to find by authId first
        userRepository.findByAuthId(request.getAuthId())
                .ifPresentOrElse(
                        user -> updateExistingUser(user, request),
                        () -> handleNewOAuthUser(request)
                );
    }

    private void updateExistingUser(User user, UserSyncRequest request) {
        user.setFirstName(request.getName());
        user.setEmail(request.getEmail());
        userRepository.save(user);
    }

    private void handleNewOAuthUser(UserSyncRequest request) {
        // Check if email exists for password-based users
        userRepository.findByEmail(request.getEmail())
                .ifPresentOrElse(
                        existingUser -> {
                            // Merge accounts if email exists
                            existingUser.setAuthId(request.getAuthId());
                            userRepository.save(existingUser);
                        },
                        () -> createNewOAuthUser(request)
                );
    }

    private void createNewOAuthUser(UserSyncRequest request) {
        User newUser = new User();
        newUser.setAuthId(request.getAuthId());
        newUser.setEmail(request.getEmail());

        String fullName = request.getName();

        if (fullName != null && fullName.contains(" ")) {
            int lastSpaceIndex = fullName.lastIndexOf(' ');
            String firstName = fullName.substring(0, lastSpaceIndex);
            String lastName = fullName.substring(lastSpaceIndex + 1);
            newUser.setFirstName(firstName);
            newUser.setLastName(lastName);
        } else {
            newUser.setFirstName(fullName);
            newUser.setLastName("");
        }

        newUser.setRole(User.Role.valueOf("traveler")); // Default role
        userRepository.save(newUser);
    }

}

