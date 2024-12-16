package com.api.login.user.controller;

import com.api.login.common.model.dto.ResultDTO;
import com.api.login.user.model.dto.UserDTO;
import com.api.login.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/user/")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("signUp")
    public ResultDTO signUp(UserDTO userDTO) {
        return userService.signUp(userDTO);
    }

    @PostMapping("existUser")
    public Boolean existUser(String id) {
        return userService.existUser(id);
    }

    @PostMapping("login")
    public ResultDTO login(UserDTO userDTO) {
        return userService.login(userDTO);
    }
}
