package com.br.API.GamesRating.settings;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.builders.ResponseMessageBuilder;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableSwagger2
public class SwaggerSettings {

  private final ResponseMessage m201 = simpleMessage(201, "Resource created");
  private final ResponseMessage m204put = simpleMessage(204, "Update ok");
  private final ResponseMessage m204del = simpleMessage(204, "Delete ok");
  private final ResponseMessage m403 = simpleMessage(403, "Not authorized");
  private final ResponseMessage m404 = simpleMessage(404, "Not found");
  private final ResponseMessage m422 = simpleMessage(422, "validation error");
  private final ResponseMessage m500 = simpleMessage(500, "Unexpected error");

  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2)
        .useDefaultResponseMessages(false)
        .globalResponseMessage(RequestMethod.GET, Arrays.asList(m403, m404, m500))
        .globalResponseMessage(RequestMethod.POST, Arrays.asList(m201, m403, m422, m500))
        .globalResponseMessage(RequestMethod.PUT, Arrays.asList(m204put, m403, m404, m422, m500))
        .globalResponseMessage(RequestMethod.DELETE, Arrays.asList(m204del, m403, m404, m500))
        .select()
        .apis(RequestHandlerSelectors.basePackage("com.br.API.GamesRating.controller"))
        .paths(PathSelectors.any())
        .build()
        .apiInfo(apiInfo())
        .securityContexts(Arrays.asList(securityContext()))
        .securitySchemes(Arrays.asList(apiKey()));
  }

  private ApiInfo apiInfo() {
    return new ApiInfo(
        "API Game Rating",
        "API developed for a mobile application.",
        "Versão 1.3.1",
        "https://github.com/welbertFC/APIGamesRating#readme",
        new Contact("Welbert Fernandes", "https://github.com/welbertFC", "welbert.jsj@gmail.com"),
        "API for exclusive use of the mobile Games Rating application",
        "https://github.com/welbertFC/APIGamesRating#readme",
        Collections.emptyList() // Vendor Extensions
        );
  }

  private ResponseMessage simpleMessage(int code, String msg) {
    return new ResponseMessageBuilder().code(code).message(msg).build();
  }

  private ApiKey apiKey() {
    return new ApiKey("JWT", "Authorization", "header");
  }

  private SecurityContext securityContext() {
    return SecurityContext.builder().securityReferences(defaultAuth()).build();
  }

  private List<SecurityReference> defaultAuth() {
    AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
    AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
    authorizationScopes[0] = authorizationScope;
    return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
  }
}
