package com.api.login.user.service.Impl;

import com.api.login.common.msg.ResultVo;
import com.api.login.user.model.entity.UserEntity;
import com.api.login.user.model.vo.UserVo;
import com.api.login.user.repo.UserRepository;
import com.api.login.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public ResultVo join(UserVo userVo) {
        if (existUser(userVo.getId())) {
            UserEntity userEntity = userVo.convertVo(userVo);
            userRepository.save(userEntity);
        } else {
            new ResultVo("", "", "");
        }
        return new ResultVo("", "", "");
    }

    public Boolean existUser(String id) {
        userRepository.findById(id);
        return false;
    }
}
