package com.api.login.user.service.Impl;

import com.api.login.common.model.enums.MessageEnum;
import com.api.login.common.model.enums.StatusEnum;
import com.api.login.common.model.enums.TypeEnum;
import com.api.login.common.model.dto.ResultDTO;
import com.api.login.common.util.crypt.Encrypt;
import com.api.login.common.util.jwt.JwtTokenUtil;
import com.api.login.common.util.redis.RedisService;
import com.api.login.user.model.dto.LoginDTO;
import com.api.login.user.model.dto.UserDTO;
import com.api.login.user.model.entity.UserEntity;
import com.api.login.user.repo.UserRepository;
import com.api.login.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final RedisService redisService;

    private final Encrypt encrypt;

    public ResultDTO signUp(UserDTO userDTO) {
        return Optional.of(userDTO)
                .filter(dto -> !existUser(dto.getId()))
                .map(dto -> {
                    String salt = encrypt.getSalt();
                    String encrytPassword = encrypt.getEncrypt(dto.getPassword(), salt);

                    UserDTO signUpDTO = UserDTO.builder()
                            .password(encrytPassword)
                            .salt(salt)
                            .build();

                    UserEntity userEntity = signUpDTO.toEntity();
                    userRepository.save(userEntity);

                    return new ResultDTO(
                            StatusEnum.SUCCESS,
                            MessageEnum.JOIN_SUCCESS.message);
                })
                .orElseGet(() -> new ResultDTO(
                        StatusEnum.DUPLICATE,
                        MessageEnum.JOIN_DUPLICATE.message));
    }

    public Boolean existUser(String id) {
        return userRepository.findById(id).isPresent();
    }

    public ResultDTO login(UserDTO userDTO) {
        Optional<UserEntity> userEntity = userRepository.findById(userDTO.getId());

        /* No Login Info - ID */
        if (userEntity.isEmpty()) {
            return new ResultDTO(StatusEnum.INVALID, MessageEnum.LOGIN_INVALID_ID.message);
        }

        UserEntity user = userEntity.get();
        String encryptedPassword = encrypt.getEncrypt(userDTO.getPassword(), user.getSalt());

        /* Not Matched - Password */
        if (!user.getPassword().equals(encryptedPassword)) {
            return new ResultDTO(StatusEnum.INVALID, MessageEnum.LOGIN_INVALID_PW.message);
        }

        String accessToken = JwtTokenUtil.createToken(user.getId(), TypeEnum.ACCESS);
        String refreshToken = JwtTokenUtil.createToken(user.getId(), TypeEnum.REFRESH);

        redisService.storeRefreshToken(user.getId(), refreshToken);

        LoginDTO loginDTO = LoginDTO.builder()
                .type(user.getType())
                .id(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return new ResultDTO(StatusEnum.SUCCESS, MessageEnum.LOGOUT_SUCCESS.message, loginDTO);
    }

    public ResultDTO logout(UserDTO userDTO) {
        redisService.deleteRefreshToken(userDTO.getId());
        return new ResultDTO(StatusEnum.SUCCESS, MessageEnum.LOGOUT_SUCCESS.message);
    }

    public String refreshAccessToken(String loginId, String refreshToken) {
        if (!redisService.isRefreshTokenValid(loginId, refreshToken)) {
            throw new RuntimeException(MessageEnum.TOKEN_NOTVALID.getMessage());
        }
        return JwtTokenUtil.createToken(loginId, TypeEnum.ACCESS);
    }
}
