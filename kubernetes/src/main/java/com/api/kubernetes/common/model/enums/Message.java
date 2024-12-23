package com.api.kubernetes.common.model.enums;

import lombok.Getter;

@Getter
public enum Message {
    /**
    *   Convention : METHOD_STATUS
    **/

    /* User Message */
    SEARCH_SUCCESS("조회가 완료되었습니다.");

    public final String message;

    Message(String message) {
        this.message = message;
    }
}
