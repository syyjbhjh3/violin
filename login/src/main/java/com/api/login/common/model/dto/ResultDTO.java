package com.api.login.common.model.dto;

import com.api.login.common.model.enums.Status;
import lombok.Getter;

@Getter
public class ResultDTO<T> {
    private Status result;
    private String resultMessage;
    private T data;

    public ResultDTO(Status result, String resultMessage, T data) {
        this.result = result;
        this.resultMessage = resultMessage;
        this.data = data;
    }

    public ResultDTO(Status result, String resultMessage) {
        this.result = result;
        this.resultMessage = resultMessage;
    }
}