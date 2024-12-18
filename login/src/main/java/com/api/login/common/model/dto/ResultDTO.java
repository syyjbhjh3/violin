package com.api.login.common.model.dto;

import com.api.login.common.model.enums.StatusEnum;
import lombok.Getter;

@Getter
public class ResultDTO<T> {
    private StatusEnum result;
    private String resultMessage;
    private T data;

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