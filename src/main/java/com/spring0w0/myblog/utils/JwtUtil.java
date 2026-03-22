package com.spring0w0.myblog.utils;

import com.spring0w0.myblog.common.exception.UnauthorizedException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * JWT 工具类
 * 用于生成和验证 JWT token
 * 基于 jjwt 0.12.x 实现
 * @author Spring0w0
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        byte[] keyBytes = secret.getBytes(StandardCharsets.UTF_8);
        this.secretKey = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * 生成 JWT token
     * @param subject 主题（通常是用户标识）
     * @return JWT token
     */
    public String generateToken(String subject) {
        Date now = new Date();
        Date expireDate = new Date(now.getTime() + expiration * 1000);

        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expireDate)
                .signWith(secretKey)
                .compact();
    }

    /**
     * 解析 JWT token
     * @param token JWT token
     * @return Claims
     * @throws UnauthorizedException 当 token 无效时抛出
     */
    public Claims parseToken(String token) {
        if (token == null || token.isEmpty()) {
            throw new UnauthorizedException("Token 不能为空");
        }
        try {
            return Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
        } catch (ExpiredJwtException e) {
            throw new UnauthorizedException("Token 已过期");
        } catch (UnsupportedJwtException e) {
            throw new UnauthorizedException("不支持的 Token 格式");
        } catch (MalformedJwtException e) {
            throw new UnauthorizedException("Token 格式错误");
        } catch (SecurityException e) {
            throw new UnauthorizedException("Token 签名验证失败");
        } catch (IllegalArgumentException e) {
            throw new UnauthorizedException("无效的 Token");
        }
    }

    /**
     * 验证 JWT token
     * @param token JWT token
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            Claims claims = parseToken(token);
            return claims.getExpiration().after(new Date());
        } catch (UnauthorizedException e) {
            return false;
        }
    }

    /**
     * 从 token 中获取主题
     * @param token JWT token
     * @return 主题
     * @throws UnauthorizedException 当 token 无效时抛出
     */
    public String getSubject(String token) {
        return parseToken(token).getSubject();
    }
}