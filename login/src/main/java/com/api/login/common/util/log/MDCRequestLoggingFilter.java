package com.api.login.common.util.log;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCRequestLoggingFilter implements Filter {

    @Value("${spring.application.name}")
    private String appName;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        MDC.put("application", appName);

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
