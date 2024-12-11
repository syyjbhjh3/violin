package com.api.login.common.model;

import lombok.Getter;

@Getter
public enum MessageEnum {
    /**
    *   Convention : METHOD_STATUS
    **/

    /* User Message */
    JOIN_SUCCESS("회원가입이 완료되었습니다."),
    JOIN_DUPLICATE("이미 존재하는 회원(이메일)입니다."),

    LOGIN_SUCCESS("로그인에 성공하였습니다."),
    LOGIN_INVALID_ID("존재하지 않는 사용자 입니다."),
    LOGIN_INVALID_PW("비밀번호를 확인해 주세요."),

    LOGOUT_SUCCESS("로그아웃에 성공하였습니다."),

    TOKEN_NOTVALID("인증되지 않은 토큰입니다.");

    public final String message;

    MessageEnum(String message) {
        this.message = message;
    }
}
