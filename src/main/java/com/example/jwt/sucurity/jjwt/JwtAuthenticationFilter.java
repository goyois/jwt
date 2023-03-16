package com.example.jwt.sucurity.jjwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//스프링 시큐리티에서 UsernamepasswordAuthenticationFilter 가 있음
//login 요청해서 username,password 전송하면 (post)
//UsernamePasswordAuthenticatinofilter 가 동작을함

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationFilter; //어댑터에 있는 매니저


    //login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("Jwt필터 로그인 시도중 ");
        //1. username.password 받아서
        //2. 정상인지 로그인 시도를 해보는 것 authenticationFilter 로 로그인 시도를 하면 PrincipalDetailService 가 호출됨
        //3. loadUserByUsername() 함수가 실행됨
        //4. PrincipalDetails를 세션에 담는다 (왜 세션이 담느냐. 세션에 값이 있어야 권한관리가 됨)
        //5. JWT 토큰을 만들어서 응답해주면 됨
        return super.attemptAuthentication(request, response);
    }
}
