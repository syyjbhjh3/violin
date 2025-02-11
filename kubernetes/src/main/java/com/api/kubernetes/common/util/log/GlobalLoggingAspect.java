package com.api.kubernetes.common.util.log;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class GlobalLoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(GlobalLoggingAspect.class);
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Around("within(@org.springframework.web.bind.annotation.RestController *)")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        HttpServletRequest request = getCurrentHttpRequest();
        if (request != null) {
            logger.info("Incoming Request: {} {}", request.getMethod(), request.getRequestURI());
            logger.info("Headers: {}", toJson(request.getHeaderNames()));
            logger.info("Parameters: {}", toJson(request.getParameterMap()));
        }

        long startTime = System.currentTimeMillis();
        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable throwable) {
            logger.error("Exception in method: {}", joinPoint.getSignature(), throwable);
            throw throwable;
        }
        long elapsedTime = System.currentTimeMillis() - startTime;

        logger.info("Response: {}", toJson(result));
        logger.info("Execution Time: {} ms", elapsedTime);

        return result;
    }

    private HttpServletRequest getCurrentHttpRequest() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        return attributes != null ? attributes.getRequest() : null;
    }

    private String toJson(Object obj) {
        try {
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (Exception e) {
            return String.valueOf(obj);
        }
    }
}