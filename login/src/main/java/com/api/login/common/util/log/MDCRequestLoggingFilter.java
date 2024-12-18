package com.api.login.common.util.log;

import jakarta.servlet.*;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class MDCRequestLoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        String uuid = UUID.randomUUID().toString();
        MDC.put("request_id", uuid);
        MDC.put("resource", "Auth");

        try {
            chain.doFilter(request, response);
        } finally {
            MDC.clear();
        }
    }
}
