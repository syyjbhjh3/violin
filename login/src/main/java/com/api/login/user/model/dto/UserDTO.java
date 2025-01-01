package com.api.login.user.model.dto;

import com.api.login.user.model.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDTO {
    private String type;
    private String id;
    private String password;
    private String name;
    private String email;
    private String address;
    private String salt;
    private String refreshToken;

    public UserEntity toEntity() {
        return new UserEntity(this.type, this.id, this.name, this.password, this.email, this.address, this.salt);
    }

    public void updateUserPassword(String password, String salt) {
        this.password = password;
        this.salt = salt;
    }
}