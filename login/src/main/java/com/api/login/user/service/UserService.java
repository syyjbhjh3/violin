package com.api.login.user.service;

import com.api.login.common.model.vo.ResultVo;
import com.api.login.user.model.vo.UserVo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    ResultVo join(UserVo userVo);
}
