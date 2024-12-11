package com.api.login.common.model.dto;

import com.api.login.common.model.StatusEnum;
import lombok.Getter;

@Getter
public class ResultDTO<T> {
    private StatusEnum result;          // 성공/실패 상태
    private String resultMessage;   // 결과 메시지
    private T data;                 // 제네릭 타입 데이터

    public ResultDTO(StatusEnum result, String resultMessage, T data) {
        this.result = result;
        this.resultMessage = resultMessage;
        this.data = data;
    }

    public ResultDTO(StatusEnum result, String resultMessage) {
        this.result = result;
        this.resultMessage = resultMessage;
    }
}