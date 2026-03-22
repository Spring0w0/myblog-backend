package com.spring0w0.myblog.interceptor;

import com.spring0w0.myblog.common.exception.UnauthorizedException;
import com.spring0w0.myblog.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 管理员认证拦截器
 * 用于验证 JWT token
 * @author Spring0w0
 */
@Component
@RequiredArgsConstructor
public class AdminInterceptor implements HandlerInterceptor {

    private final JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        // 从请求头中获取 token
        String token = request.getHeader("Authorization");
        
        // 检查 token 是否存在
        if (token == null || token.isEmpty()) {
            throw new UnauthorizedException("未提供认证 Token");
        }
        
        // 处理 Bearer token 格式
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        
        // 验证 token（失败会抛出 UnauthorizedException）
        jwtUtil.parseToken(token);
        
        return true;
    }
}
