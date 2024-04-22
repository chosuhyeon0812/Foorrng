package com.d205.foorrng.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@OpenAPIDefinition(
    info = @Info(
            title = "Foorrng",
            description = "빅데이터 활용한 위치 추천 서비스입니다.",
            version = "1.0"
    )
)

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        // 인증 요청 방식에 HEADER token 추가
        SecurityScheme securityScheme = new SecurityScheme()
                .type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER).name("Authorization");
//                .in(SecurityScheme.In.HEADER).name("Access-Token");

        SecurityRequirement securityRequirement = new SecurityRequirement().addList("bearerAuth");

        return new OpenAPI()
                // SercurityRequirment에 정의한 bearerAuth, 위에서 정의한 security Scheme 추가
                .components(new Components().addSecuritySchemes("bearerAuth", securityScheme))
                // 보안 규칙 추가
                .security(Arrays.asList(securityRequirement))
                .addServersItem(new Server().url("/"));
    }

}
