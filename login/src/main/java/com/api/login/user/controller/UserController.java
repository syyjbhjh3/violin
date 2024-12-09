package com.api.login.user.controller;

import com.api.login.common.model.vo.ResultVo;
import com.api.login.user.model.vo.UserVo;
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

    @PostMapping("join")
    public ResultVo join(UserVo userVo) {
        return userService.join(userVo);
    }

    @PostMapping("login")
    public ResultVo login(UserVo userVo) {
        return userService.login(userVo);
    }
}
