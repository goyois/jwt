package com.example.jwt.sucurity.config;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션 안씀
                .and()
                .formLogin().disable()  //jwt서버이기 떄문에 아이디 로그인을 폼 로그인으로 안함
                .httpBasic().disable()
                .authorizeRequests()
                .antMatchers("/api/v1/user/**")  //해당 주소로 요청이 들어오면
                .access("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")  //user , admin 가능
                .antMatchers("/api/v1/admin/**")
                .access("hasRole('ROLE_ADMIN')") //admin만 가능
                .anyRequest().permitAll();  


    }
}
