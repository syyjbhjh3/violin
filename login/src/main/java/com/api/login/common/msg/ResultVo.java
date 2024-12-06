package com.api.login.common.msg;

import lombok.Getter;

@Getter
public class ResultVo {
    private String result;
    private String resultCode;
    private String resultMessage;

    public ResultVo(String result, String resultCode, String resultMessage) {
        this.result = result;
        this.resultCode = resultCode;
        this.resultMessage = resultMessage;
    }
}
