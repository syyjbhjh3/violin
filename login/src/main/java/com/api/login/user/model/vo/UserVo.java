package com.api.login.user.model.vo;

import com.api.login.user.model.entity.UserEntity;
import lombok.Getter;

@Getter
public class UserVo {
    private String type;
    private String id;
    private String password;
    private String name;
    private String gender;
    private String phone;
    private String email;
    private String address;

    public UserVo(String type, String name, String id, String password, String gender, String phone, String email, String address) {
        this.type = type;
        this.name = name;
        this.id = id;
        this.password = password;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public UserEntity convertVo(UserVo userVo) {
        UserEntity user = new UserEntity();

        user.setType(userVo.getType());
        user.setId(userVo.getId());
        user.setPassword(userVo.getPassword());
        user.setName(userVo.getName());
        user.setGender(userVo.getGender());
        user.setPhone(userVo.getPhone());
        user.setEmail(userVo.getEmail());
        user.setAddress(userVo.getAddress());

        return user;
    }
}
