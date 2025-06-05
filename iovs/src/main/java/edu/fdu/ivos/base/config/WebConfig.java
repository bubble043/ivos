package edu.fdu.ivos.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

// 1. Add @Configuration annotation
@Configuration
public class WebConfig implements WebMvcConfigurer { // 2. Implement WebMvcConfigurer
    // 3. Type addCors, then press enter when prompted to auto-complete method
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 4. Remove default method body and define your own CORS policy
        registry.addMapping("/**") // /** allows matching any client request path
                .allowedHeaders("*") // allows requests with any headers
                .allowedMethods("*") // allows all request methods (GET, POST, PUT, etc.)
                .allowedOriginPatterns("*") // allows requests from any domain or port
                .allowCredentials(true) // allows sending session-related info (cookies/sessions)
                .maxAge(3600); // skips preflight check for the same request within 1 hour
    }
}
