package edu.fdu.ivos.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

@Configuration
@Component
@EnableSwagger2WebMvc
public class Knife4jConfig {
    // Configure the Docket Bean instance for Swagger2
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                // apiInfo(): Set some basic information for the API, such as title, description, version
                .apiInfo(apiInfo())
                // select(): API document selector for choosing which APIs to include
                .select()
                // apis(): Specify which package's APIs to include in the documentation
                .apis(RequestHandlerSelectors.basePackage("cn.tedu.ivos"))
                // paths(): Specify which URL patterns to include. PathSelectors.any() includes all APIs.
                .paths(PathSelectors.any())
                .build();
    }

    // API documentation metadata configuration
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // API document title
                .title("Smart Vehicle Operation Project")
                // API document description
                .description("Online API documentation for the Smart Vehicle Operation Project")
                // API document version
                .version("1.0")
                .build();
    }
}
