package com.navec.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.any;

@Configuration
public class SwaggerConfig {
    public static final String AUTHORIZATION = "AUTHORIZATION";

    @Bean
    public Docket productApi() {
        return new Docket(DocumentationType.OAS_30)
                .securityContexts(Collections.singletonList(securityContext()))
                .securitySchemes(List.of(apiKey()))
                .apiInfo(new ApiInfo("Automoto", "Automoto API", "1.0.0", "", new Contact("ivelinov", "", "wankishh@gmail.com"), "", "", new ArrayList<>()))
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(any())
                .build();
    }

    private ApiKey apiKey() {
        return new ApiKey(AUTHORIZATION, "JWT", "header");
    }

    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return List.of(new SecurityReference(AUTHORIZATION, authorizationScopes));
    }
}