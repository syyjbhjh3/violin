package com.api.gateway.filter.globlaFilter;

import io.micrometer.tracing.Tracer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.RouteMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class RequestGlobalFilter implements GlobalFilter, Ordered {

    private static final Logger logger = LoggerFactory.getLogger(RequestGlobalFilter.class);

    Tracer tracer;
    public RequestGlobalFilter(Tracer tracer) {
        this.tracer = tracer;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        String traceId = tracer.currentSpan().context().traceId();
        RouteMatcher.Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);

        exchange.getRequest().mutate()
                .header("X-Trace-Id", traceId)
                .build();

        logger.info("Request - TraceId: {}, Method: {}, Service: {}, Path: {}, Params: {}",
                traceId,
                request.getMethod(),
                route,
                request.getPath(),
                request.getQueryParams());

        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
