package org.kaanalkim.authserver.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Collections;
import java.util.List;

@Configuration
public class SwaggerConfiguration {
    @Configuration
    public static class SpringFoxConfig {
        @Bean
        public Docket api() {
            return new Docket(DocumentationType.SWAGGER_2)
                    .apiInfo(apiInfo())
                    .securityContexts(Collections.singletonList(securityContext()))
                    .securitySchemes(Collections.singletonList(apiKey()))
                    .select()
                    .apis(RequestHandlerSelectors.any())
                    .paths(PathSelectors.any())
                    .build();
        }

        private SecurityContext securityContext() {
            return SecurityContext.builder().securityReferences(defaultAuth()).build();
        }

        private List<SecurityReference> defaultAuth() {
            AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
            AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
            authorizationScopes[0] = authorizationScope;
            return List.of(new SecurityReference("JWT", authorizationScopes));
        }

        private ApiKey apiKey() {
            return new ApiKey("JWT", "Authorization", "header");
        }

        private ApiInfo apiInfo() {
            return new ApiInfo(
                    "Auth Server REST API",
                    "Some custom description of API.",
                    "1.0",
                    "Terms of service",
                    new Contact("Guner Kaan ALKIM", "", "g.kaanalkim@gmail.com"),
                    "License of API",
                    "API license URL",
                    Collections.emptyList());
        }
    }
}