package com.api.gateway.filter;

import com.api.gateway.redis.RedisService;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class PreGatewayAuthorizationFilterFactory extends AbstractGatewayFilterFactory<PreGatewayAuthorizationFilterFactory.Config> {
    private final TokenProvider tokenProvider;
    public PreGatewayAuthorizationFilterFactory(TokenProvider tokenProvider, RedisService redisService){
        super(Config.class);
        this.tokenProvider = tokenProvider;
        this.redisService = redisService;
    }

    private final RedisService redisService;

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String authorizationHeader = exchange.getRequest().getHeaders().getFirst(config.headerName);
            if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith(config.granted+" ")) {
                String token = authorizationHeader.substring(7); // Bearer
                try {
                    if (tokenProvider.isExpired(token)) {
                        String loginId = tokenProvider.getLoginId(token);

                        /* Redis Check */
                        if (redisService.isUser(loginId)) {
                            return chain.filter(exchange);
                        } else {
                            return unauthorizedResponse(exchange);
                        }
                    }
                } catch (Exception e) {
                    log.error("Token validation error: {}", e.getMessage());
                }
            }
            return unauthorizedResponse(exchange);
        };
    }

    private Mono<Void> unauthorizedResponse(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Getter
    @Setter
    public static class Config{
        private String headerName;
        private String granted;
    }
}
