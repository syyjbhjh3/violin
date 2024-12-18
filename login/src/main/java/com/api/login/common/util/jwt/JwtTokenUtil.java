package com.api.login.common.util.jwt;

import com.api.login.common.model.enums.TypeEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenUtil {

    @Value("${user.jwt.secret}")
    private String secretKey;

    @Value("${user.jwt.expiration.access}")
    private long accessTokenExpiration;

    @Value("${user.jwt.expiration.refresh}")
    private long refreshTokenExpiration;

    public String createToken(String loginId, TypeEnum status) {
        long expiration = status == TypeEnum.ACCESS ? accessTokenExpiration : refreshTokenExpiration;

        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public String getLoginId(String token) {
        return extractClaims(token).get("loginId").toString();
    }

    public boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }

    private Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
