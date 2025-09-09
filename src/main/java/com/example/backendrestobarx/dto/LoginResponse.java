package com.example.backendrestobarx.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {

    private String token;
    private UserDto user;
    private String message;

    public LoginResponse(String token, UserDto user) {
        this.token = token;
        this.user = user;
        this.message = "Login exitoso";
    }
}