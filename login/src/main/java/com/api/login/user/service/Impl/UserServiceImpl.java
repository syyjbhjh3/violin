package com.api.login.user.service.Impl;

import com.api.login.common.model.enums.Message;
import com.api.login.common.model.enums.Status;
import com.api.login.common.model.enums.Type;
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

    private final JwtTokenUtil jwtTokenUtil;

    public ResultDTO signUp(UserDTO userDTO) {
        return Optional.of(userDTO)
                .filter(dto -> !existUser(dto.getId()))
                .map(dto -> {
                    String salt = null, encrytPassword = null;

                    if (dto.getType().equals("1")) {
                        salt = encrypt.getSalt();
                        encrytPassword = encrypt.getEncrypt(dto.getPassword(), salt);
                    }

                    UserDTO signUpDTO = UserDTO.builder()
                            .type(dto.getType())
                            .id(dto.getId())
                            .name(dto.getName())
                            .gender(dto.getGender())
                            .phone(dto.getPhone())
                            .email(dto.getEmail())
                            .address(dto.getAddress())
                            .password(encrytPassword)
                            .salt(salt)
                            .build();

                    UserEntity userEntity = signUpDTO.toEntity();
                    userRepository.save(userEntity);

                    return new ResultDTO(
                            Status.SUCCESS,
                            Message.JOIN_SUCCESS.message);
                })
                .orElseGet(() -> new ResultDTO(
                        Status.DUPLICATE,
                        Message.JOIN_DUPLICATE.message));
    }

    public Boolean existUser(String id) {
        return userRepository.findById(id).isPresent();
    }

    public ResultDTO login(UserDTO userDTO) {
        Optional<UserEntity> userEntity = userRepository.findById(userDTO.getId());

        /* No Login Info - ID */
        if (userEntity.isEmpty()) {
            return new ResultDTO(Status.INVALID, Message.LOGIN_INVALID_ID.message);
        }

        UserEntity user = userEntity.get();
        String encryptedPassword = encrypt.getEncrypt(userDTO.getPassword(), user.getSalt());

        /* Not Matched - Password */
        if (!user.getPassword().equals(encryptedPassword)) {
            return new ResultDTO(Status.INVALID, Message.LOGIN_INVALID_PW.message);
        }

        String accessToken = jwtTokenUtil.createToken(user.getId(), Type.ACCESS);
        String refreshToken = jwtTokenUtil.createToken(user.getId(), Type.REFRESH);

        redisService.storeRefreshToken(user.getId(), refreshToken);

        LoginDTO loginDTO = LoginDTO.builder()
                .type(user.getType())
                .id(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return new ResultDTO(Status.SUCCESS, Message.LOGIN_SUCCESS.message, loginDTO);
    }

    public ResultDTO oAuthLogin(String id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if (userEntity.isEmpty()) {
            return new ResultDTO(Status.INVALID, Message.LOGIN_INVALID_ID.message);
        }

        UserEntity user = userEntity.get();

        String accessToken = jwtTokenUtil.createToken(user.getId(), Type.ACCESS);
        String refreshToken = jwtTokenUtil.createToken(user.getId(), Type.REFRESH);

        redisService.storeRefreshToken(user.getId(), refreshToken);

        LoginDTO loginDTO = LoginDTO.builder()
                .type(user.getType())
                .id(user.getId())
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();

        return new ResultDTO(Status.SUCCESS, Message.LOGIN_SUCCESS.message, loginDTO);
    }

    public ResultDTO logout(UserDTO userDTO) {
        redisService.deleteRefreshToken(userDTO.getId());
        return new ResultDTO(Status.SUCCESS, Message.LOGOUT_SUCCESS.message);
    }

    public String refreshAccessToken(String loginId, String refreshToken) {
        if (!redisService.isRefreshTokenValid(loginId, refreshToken)) {
            throw new RuntimeException(Message.TOKEN_NOTVALID.getMessage());
        }
        return jwtTokenUtil.createToken(loginId, Type.ACCESS);
    }
}
