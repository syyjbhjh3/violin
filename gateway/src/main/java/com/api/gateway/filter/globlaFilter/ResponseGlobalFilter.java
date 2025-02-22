package com.api.gateway.filter.globlaFilter;

import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class ResponseGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(ResponseGlobalFilter.class);
    Tracer tracer;

    public ResponseGlobalFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            ServerHttpResponse response = exchange.getResponse();
            String traceId = response.getHeaders().getFirst("X-Trace-Id");

            if (!response.isCommitted()) {
                if (traceId == null) {
                    traceId = tracer.currentSpan() != null ? tracer.currentSpan().context().traceId() : "UNKNOWN";
                    response.getHeaders().add("X-Trace-Id", traceId);
                }
            }

            logger.info("Response - TraceId: {}, Status Code: {}",
                    traceId,
                    response.getStatusCode()
            );
        }));
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
