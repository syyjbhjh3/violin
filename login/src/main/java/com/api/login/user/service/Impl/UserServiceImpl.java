package com.api.login.user.service.Impl;

import com.api.login.common.model.MessageEnum;
import com.api.login.common.model.vo.ResultVo;
import com.api.login.user.model.entity.UserEntity;
import com.api.login.user.model.vo.UserVo;
import com.api.login.user.repo.UserRepository;
import com.api.login.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public ResultVo join(UserVo userVo) {
        return Optional.of(userVo)
                .filter(vo -> !existUser(vo.getId()))
                .map(vo -> {
                    UserEntity userEntity = vo.convertVo(vo);
                    userRepository.save(userEntity);
                    return new ResultVo(
                            MessageEnum.RESULT_SUCCESS.message,
                            MessageEnum.JOIN_SUCCESS.message);
                })
                .orElseGet(() -> new ResultVo(
                        MessageEnum.RESULT_ERROR.message,
                        MessageEnum.EXIST_USER.message));
    }

    public Boolean existUser(String id) {
        return userRepository.findById(id).isPresent();
    }
}
