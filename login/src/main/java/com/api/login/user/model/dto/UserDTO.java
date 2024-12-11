package com.api.login.user.model.dto;

import com.api.login.common.model.StatusEnum;
import com.api.login.user.model.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;

@Getter
public class UserDTO {
    private String type;
    private String id;
    private String password;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String address;

    @Builder
    public UserDTO(String type, String id, String password, String name, String gender, String phone, String email, String address) {
        this.type = type;
        this.id = id;
        this.password = password;
        this.name = name;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public UserEntity toEntity() {
        return new UserEntity(this.type, this.id, this.password, this.name, this.gender, this.phone, this.email, this.address);
    }
}