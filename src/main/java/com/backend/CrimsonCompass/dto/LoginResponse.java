package com.backend.CrimsonCompass.dto;

import com.backend.CrimsonCompass.model.User;

public class LoginResponse {
    private String message;
    private User data;

    private String accessToken;
    private String tokenType = "Bearer";

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}
