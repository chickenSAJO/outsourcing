package xyz.tomorrowlearncamp.outsourcing.global.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
// Swagger Config
public class OpenApiConfig {
    @Bean
    public OpenAPI openAPI(@Value("${springdoc.version}") String springdocVersion) {
        Info info = new Info()
                .title("CateQuest API Docs")
                .version(springdocVersion)
                .description("CateQuest 서버 API Docs");

        return new OpenAPI()
                .components(new Components())
                .info(info);
    }
}

