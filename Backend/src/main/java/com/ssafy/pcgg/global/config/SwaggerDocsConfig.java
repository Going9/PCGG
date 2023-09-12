package com.ssafy.pcgg.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class SwaggerDocsConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info() // Swagger의 설명을 적는 객체입니다.
                .title("PC.GG API Document")
                .version("v0.0.1")
//                .version(@Value("${springdoc.version}") String version) // 문서 버전
//                .contact(new Contact() // 연락처
//                    .name("beekei")
//                    .email("beekei.shin@gmail.com")
//                    .url("https://devbksheen.tistory.com/"))
                .description("PC.GG의 API 명세서입니다.");

        // Security 스키마 설정
        SecurityScheme bearerAuth = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);

        // Security 요청 설정
        SecurityRequirement addSecurityItem = new SecurityRequirement();
        addSecurityItem.addList("JWT");

        return new OpenAPI()
                // Security 인증 컴포넌트 설정
                .components(new Components().addSecuritySchemes("JWT", bearerAuth))
                // API 마다 Security 인증 컴포넌트 설정
                .addSecurityItem(addSecurityItem)
                .info(info);

    }
}
