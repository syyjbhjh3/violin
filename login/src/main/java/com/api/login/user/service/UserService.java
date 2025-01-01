package com.api.login.user.service;

import com.api.login.common.model.dto.ResultDTO;
import com.api.login.user.model.dto.UserDTO;
import org.springframework.stereotype.Service;


@Service
public interface UserService {
    ResultDTO signUp(UserDTO userDTO);

    Boolean existUser(UserDTO userDTO);

    Boolean existUser(String id);

    ResultDTO login(UserDTO userDTO);

    ResultDTO oAuthLogin(UserDTO userDTO);

    ResultDTO refresh(UserDTO userDTO);
}
