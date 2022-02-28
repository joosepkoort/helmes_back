package com.helmes.proovitoo.conf;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.helmes.proovitoo.controllers", "com.helmes.proovitoo.conf", "com.helmes.proovitoo.dao", "com.helmes.proovitoo.util", "com.helmes.proovitoo.model"})
@Import(JpaConfig.class)
public class SpringConfig implements WebMvcConfigurer {
    @Override
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**").allowedOriginPatterns("turing.cs.ttu.ee", "http://localhost:4200")
                .allowedMethods("HEAD", "GET", "PUT", "POST", "DELETE", "PATCH", "OPTIONS")
                .allowedHeaders("Content-Type", "X-Requested-With", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers", "Access-Control-Allow-Origin", "responseType")
                .exposedHeaders("Access-Control-Allow-Origin", "Access-Control-Allow-Credentials", "responseType")
                .allowCredentials(true).maxAge(3600);
    }

}
