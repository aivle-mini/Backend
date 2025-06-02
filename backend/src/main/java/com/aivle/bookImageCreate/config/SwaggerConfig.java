package com.aivle.bookImageCreate.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        Info info = new Info()
                .version("1.0")
                .title("Book Service API")
                .description("Book API Description");

        return new OpenAPI()
                .info(info);
    }
}
