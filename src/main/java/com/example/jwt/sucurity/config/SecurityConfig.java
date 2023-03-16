package com.example.jwt.sucurity.config;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CorsFilter corsFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)// 쿠키 세션 사용 x
                .and()
                .addFilter(corsFilter)  //필터가 cors 정책에서 벗어날 수 있으므로 cors 오류 해결
                .formLogin().disable()  //jwt서버이기 떄문에 아이디 로그인을 폼 로그인으로 안함
                .httpBasic().disable()  //기본 인증 방식 x
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")  //해당 주소로 요청이 들어오면
                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")  //user , admin 가능
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')") //admin만 가능
                .anyRequest().permitAll();  //jwt 기본 config 설정
    }
}
