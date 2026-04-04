package com.spring0w0.myblog.config;

import com.spring0w0.myblog.interceptor.AdminInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Spring0w0
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${upload.path}")
    private String uploadPath;

    @Value("${spring.profiles.active}")
    private String activeProfile; // 获取当前环境标识

    private final AdminInterceptor adminInterceptor;

    public WebConfig(AdminInterceptor adminInterceptor) {
        this.adminInterceptor = adminInterceptor;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if ("dev".equals(activeProfile)) {
            // 配置静态资源处理器，将 /uploads/** 映射到 uploadPath 目录
            registry.addResourceHandler("/uploads/**")
                    .addResourceLocations("file:" + uploadPath);
        }
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 配置拦截器，拦截需要认证的请求
        registry.addInterceptor(adminInterceptor)
                .addPathPatterns("/admin/**")
                .excludePathPatterns("/login");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        if ("dev".equals(activeProfile)) {
            // 配置CORS，允许跨域请求
            registry.addMapping("/**")
                    .allowedOrigins("*")
                    .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    }
}
