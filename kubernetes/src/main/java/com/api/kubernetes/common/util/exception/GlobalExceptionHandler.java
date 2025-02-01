package com.api.kubernetes.common.util.exception;

import com.api.kubernetes.common.model.dto.ResultDTO;
import com.api.kubernetes.common.model.enums.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResultDTO handleException(Exception e) {
        log.error(e.getMessage(), e);
        return new ResultDTO<>(Status.ERROR, e.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public ResultDTO handleException(IOException e) {
        log.error(e.getMessage(), e);
        return new ResultDTO<>(Status.ERROR, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResultDTO handleException(IllegalArgumentException e) {
        log.error(e.getMessage(), e);
        return new ResultDTO<>(Status.ERROR, e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResultDTO handleException(RuntimeException e) {
        log.error(e.getMessage(), e);
        return new ResultDTO<>(Status.ERROR, e.getMessage());
    }
}
