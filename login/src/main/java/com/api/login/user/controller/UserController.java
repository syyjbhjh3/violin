package com.api.login.user.controller;

import com.api.login.common.msg.ResultVo;
import com.api.login.user.model.entity.UserEntity;
import com.api.login.user.model.vo.UserVo;
import com.api.login.user.repo.UserRepository;
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

    private final UserRepository userRepository;

    @PostMapping("join")
    public ResultVo join(UserVo userVo) {
        UserEntity userEntity = userVo.convertVo(userVo);
        userRepository.save(userEntity);
        return new ResultVo("", "", "");
    }
}
