package com.api.login.user.model.dto;

import com.api.login.user.model.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDTO {
    private final String type;
    private final String id;
    private final String password;
    private final String name;
    private final String gender;
    private final String phone;
    private final String email;
    private final String address;
    private final String salt;

    public UserEntity toEntity() {
        return new UserEntity(this.type, this.id, this.name, this.password, this.gender, this.phone, this.email, this.address, this.salt);
    }
}