package com.api.login.common.model.vo;

import lombok.Getter;

@Getter
public class ResultVo {
    private String result;
    private String resultMessage;

    public ResultVo(String result, String resultMessage) {
        this.result = result;
        this.resultMessage = resultMessage;
    }
}
