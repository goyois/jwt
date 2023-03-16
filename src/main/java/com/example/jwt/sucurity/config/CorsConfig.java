package com.example.jwt.sucurity.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);  //내 서버가 응답을 할 때 json을 자바스크립트가 받을 수 있도록 하는 것(true)
        config.addAllowedOrigin("*"); //모든 ip 허용
        config.addAllowedHeader("*");  //모든 header 허용
        config.addAllowedMethod("*");  //모든 Http Method 요청 허용
        config.addExposedHeader("Authorization");
        source.registerCorsConfiguration("/api/**", config);
        return new CorsFilter(source);
    }
}
