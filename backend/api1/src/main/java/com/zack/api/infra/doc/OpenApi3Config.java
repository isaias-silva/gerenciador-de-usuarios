package com.zack.api.infra.doc;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(info = @Info(title = "MediaCodecApi",
        version = "v1", description = "api de uma rede social fictícia. com gerenciamento de usuário",
        contact = @Contact(email = "isaiasgarraeluta@gmail.com", url = "https://github.com/isaias-silva")
))
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer",
        description = "json web token para autentificação"
)
public class OpenApi3Config {
    @Bean
    public GroupedOpenApi userGroup() {
        return GroupedOpenApi.builder()
                .group("user-api")
                .displayName("user endpoints")
                .pathsToMatch( "/user/**")
                .build();
    }
    @Bean
    public GroupedOpenApi admGroup() {
        return GroupedOpenApi.builder()
                .group("adm-api")
                .displayName("adm endpoints")
                .pathsToMatch( "/adm/**")
                .build();
    }
}