package com.api.login.user.service.Impl;

import com.api.login.common.model.MessageEnum;
import com.api.login.common.model.StatusEnum;
import com.api.login.common.model.vo.ResultVo;
import com.api.login.common.util.crypt.Encrypt;
import com.api.login.common.util.jwt.JwtTokenUtil;
import com.api.login.common.util.redis.RedisService;
import com.api.login.user.model.entity.UserEntity;
import com.api.login.user.model.vo.LoginVo;
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

    public LoginVo login(UserVo userVo) {
        Optional<UserEntity> userEntity = userRepository.findById(userVo.getId());

        /* No Login Info - ID */
        if (userEntity.isEmpty()) {
            return LoginVo.error("", "No matching user ID");
        }

        UserEntity user = userEntity.get();
        String encryptedPassword = encrypt.getEncrypt(userVo.getPassword(), user.getSalt());

        /* Not Matched - Password */
        if (!user.getPassword().equals(encryptedPassword)) {
            return LoginVo.error("", "Incorrect password");
        }

        String accessToken = JwtTokenUtil.createToken(user.getId(), StatusEnum.ACCESS);
        String refreshToken = JwtTokenUtil.createToken(user.getId(), StatusEnum.REFRESH);

        redisService.storeRefreshToken(user.getId(), refreshToken);
        return LoginVo.success(user.getType(), user.getId(), accessToken, refreshToken);
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
