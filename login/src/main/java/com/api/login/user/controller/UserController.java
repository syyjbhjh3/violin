package com.api.login.user.controller;

import com.api.login.common.model.dto.ResultDTO;
import com.api.login.user.model.dto.UserDTO;
import com.api.login.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signUp")
    public ResultDTO signUp(@RequestBody UserDTO userDTO) {
        return userService.signUp(userDTO);
    }

    @PostMapping("/existUser")
    public Boolean existUser(@RequestBody UserDTO userDTO) {
        return userService.existUser(userDTO);
    }

    @PostMapping("/login")
    public ResultDTO login(@RequestBody UserDTO userDTO) {
        return userService.login(userDTO);
    }

    @PostMapping("/oAuthLogin")
    public ResultDTO oAuthLogin(@RequestBody UserDTO userDTO) {
        return userService.oAuthLogin(userDTO);
    }
}
