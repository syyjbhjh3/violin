package com.api.login.user.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
public class LoginDTO {
    private String type;
    private String id;
    private String accessToken;
    private String refreshToken;

    @Builder
    public LoginDTO(String type, String id, String accessToken, String refreshToken) {
        this.type = type;
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }
}