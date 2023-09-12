package com.ssafy.pcgg.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerDocsConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info() // Swagger의 설명을 적는 객체입니다.
                .title("PC.GG API Document")
                .version("v0.0.1")
                .description("PC.GG의 API 명세서입니다.");
        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}
