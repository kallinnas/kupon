package com.system.kupon.rest;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RestConfiguration {

    @Bean(name = "tokens")
//    @Scope(BeanDefinition.SCOPE_SINGLETON) - by default
    public Map<String, ClientSession> tokensMap() {
        return new HashMap<>();
    }

//    @Bean
//    public WebMvcConfigurer corsConfigurer() {
//        return new WebMvcConfigurerAdapter() {
//            @Override
//            public void addCorsMappings(CorsRegistry registry) {
//                registry.addMapping("/**").allowedMethods("GET", "POST", "PUT", "DELETE").allowedOrigins("localhost:")
//                        .allowedHeaders("*").allowCredentials(true);
//            }
//        };
//    }

}
