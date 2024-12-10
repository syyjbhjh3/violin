package com.api.login.common.util.jwt;

import com.api.login.common.model.StatusEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.Date;

@RequiredArgsConstructor
public class JwtTokenUtil {

    @Value("${user.jwt.secret}")
    private static String secretKey;

    @Value("${user.jwt.expiration.access}")
    private static long accessTokenExpiration;

    @Value("${user.jwt.expiration.refresh}")
    private static long refreshTokenExpiration;

    public static String createToken(String loginId, StatusEnum status) {
        long expiration = status == StatusEnum.ACCESS ? accessTokenExpiration : refreshTokenExpiration;

        Claims claims = Jwts.claims();
        claims.put("loginId", loginId);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    public static String getLoginId(String token) {
        return extractClaims(token).get("loginId").toString();
    }

    public static boolean isExpired(String token) {
        Date expiredDate = extractClaims(token).getExpiration();
        return expiredDate.before(new Date());
    }

    private static Claims extractClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }
}
