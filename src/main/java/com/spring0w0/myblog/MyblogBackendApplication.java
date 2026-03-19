package com.spring0w0.myblog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan("com.spring0w0.myblog.mapper")  // 扫描 Mapper 接口
public class MyblogBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyblogBackendApplication.class, args);
    }

}