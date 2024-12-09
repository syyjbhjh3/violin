package com.api.login.common.model;

import lombok.Getter;

@Getter
public enum MessageEnum {
    /* Result Message */
    RESULT_SUCCESS("success"),
    RESULT_ERROR("error"),

    /* Method Message */
    JOIN_SUCCESS("회원가입이 완료되었습니다."),
    EXIST_USER("이미 존재하는 회원(이메일)입니다.");

    public final String message;

    MessageEnum(String message) {
        this.message = message;
    }
}
