package com.hunnit_beasts.kelog.common.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(name = "bearerAuth",type = SecuritySchemeType.HTTP,bearerFormat = "JWT",scheme = "bearer")
public class SwaggerConfig {

        @Bean
        public OpenAPI openAPI() {
                return new OpenAPI()
                        .info(apiInfo());
        }

        private Info apiInfo() {
                return new Info()
                        .title("Kelog API Documentaion")
                        .description("Kelogs Swagger UI APIs")
                        .version("v1.0.0");
        }

}