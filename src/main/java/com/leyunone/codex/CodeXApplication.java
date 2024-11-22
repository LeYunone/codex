package com.leyunone.codex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = {"com.leyunone.codex"})
public class CodeXApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(CodeXApplication.class, args);
    }


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(CodeXApplication.class);
    }
}
