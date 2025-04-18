package com.backend.CrimsonCompass.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSyncRequest {
    private String authId;
    private String email;
    private String name;

    public UserSyncRequest() {}

    public UserSyncRequest(String authId, String email, String name) {
        this.authId = authId;
        this.email = email;
        this.name = name;
    }
}
