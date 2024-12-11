package com.api.login.user.model.vo;

import com.api.login.user.model.entity.UserEntity;
import lombok.Getter;

@Getter
public class LoginVo {
    private String type;
    private String id;
    private String accessToken;
    private String refreshToken;
    private String errCode;
    private String errMsg;

    private LoginVo(String type, String id, String accessToken, String refreshToken, String errCode, String errMsg) {
        this.type = type;
        this.id = id;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.errCode = errCode;
        this.errMsg = errMsg;
    }

    /* Factory Method */
    public static LoginVo success(String type, String id, String accessToken, String refreshToken) {
        return new LoginVo(type, id, accessToken, refreshToken, null, null);
    }

    public static LoginVo error(String errCode, String errMsg) {
        return new LoginVo(null, null, null, null, errCode, errMsg);
    }
}
