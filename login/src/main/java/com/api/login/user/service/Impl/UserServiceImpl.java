package com.api.login.user.service.Impl;

import com.api.login.common.model.MessageEnum;
import com.api.login.common.model.StatusEnum;
import com.api.login.common.model.vo.ResultVo;
import com.api.login.common.util.crypt.Encrypt;
import com.api.login.common.util.jwt.JwtTokenUtil;
import com.api.login.common.util.redis.RedisService;
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

    private final RedisService redisService;

    private final Encrypt encrypt;

    public ResultVo join(UserVo userVo) {

        return Optional.of(userVo)
                .filter(vo -> !existUser(vo.getId()))
                .map(vo -> {
                    UserEntity userEntity = vo.convertVo(vo);

                    String salt = encrypt.getSalt();
                    String encrytPassword = encrypt.getEncrypt(vo.getPassword(), salt);

                    userEntity.setPassword(encrytPassword);
                    userRepository.save(userEntity);

                    return new ResultVo(
                            MessageEnum.RESULT_SUCCESS.message,
                            MessageEnum.JOIN_SUCCESS.message);
                })
                .orElseGet(() -> new ResultVo(
                        MessageEnum.RESULT_ERROR.message,
                        MessageEnum.EXIST_USER.message));
    }

    public ResultVo login(UserVo userVo) {
        Optional<UserEntity> user = userRepository.findById(userVo.getId());

        if (user.isPresent()) {
            UserEntity userEntity = user.get();

            if (userEntity.getPassword().equals(encrypt.getEncrypt(userVo.getPassword(), userEntity.getSalt()))) {
                String accessToken = JwtTokenUtil.createToken(userEntity.getId(), StatusEnum.ACCESS);
                String refreshToken = JwtTokenUtil.createToken(userEntity.getId(), StatusEnum.REFRESH);
                redisService.storeRefreshToken(userEntity.getId(), refreshToken);


            } else {
                /* Not Matched - Password */
            }
        } else {
            /* No Login Info - ID */
        }

        return new ResultVo(
                MessageEnum.RESULT_SUCCESS.message,
                MessageEnum.JOIN_SUCCESS.message);
    }

    public ResultVo logout(UserVo userVo) {
        redisService.deleteRefreshToken(userVo.getId());
        return new ResultVo("", "");
    }

    public Boolean existUser(String id) {
        return userRepository.findById(id).isPresent();
    }

    public String refreshAccessToken(String loginId, String refreshToken) {
        if (!redisService.isRefreshTokenValid(loginId, refreshToken)) {
            throw new RuntimeException("Invalid or expired refresh token.");
        }
        return JwtTokenUtil.createToken(loginId, StatusEnum.ACCESS);
    }
}
