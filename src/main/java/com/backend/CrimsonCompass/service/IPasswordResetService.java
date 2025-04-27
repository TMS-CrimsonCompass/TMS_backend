package com.backend.CrimsonCompass.service;

public interface IPasswordResetService {

    void initiateReset(String email) throws Exception;
    void resetPassword(String token, String newPassword);

}
