package com.api.login.common.model.vo;

import com.api.login.common.model.StatusEnum;
import lombok.Getter;

@Getter
public class ResultVo {
    private StatusEnum result;
    private String resultMessage;

    public ResultVo(StatusEnum result, String resultMessage) {
        this.result = result;
        this.resultMessage = resultMessage;
    }
}
