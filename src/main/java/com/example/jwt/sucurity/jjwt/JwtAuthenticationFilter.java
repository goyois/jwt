package com.example.jwt.sucurity.jjwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.example.jwt.sucurity.auth.PrincipalDetails;
import com.example.jwt.user.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Date;



//스프링 시큐리티에서 UsernamepasswordAuthenticationFilter 가 있음
//login 요청해서 username,password 전송하면 (post)
//UsernamePasswordAuthenticatinofilter 가 동작을함

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager; //어댑터에 있는 매니저


    //login 요청을 하면 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        System.out.println("Jwt필터 로그인 시도중 ");

        try {

            ObjectMapper om = new ObjectMapper();
            User user = om.readValue(request.getInputStream(),User.class);
            System.out.println(user);

            UsernamePasswordAuthenticationToken authenticationToken =
                    new UsernamePasswordAuthenticationToken(user.getUsername(),user.getPassword());
            Authentication authentication = authenticationManager.authenticate(authenticationToken);
            //authentication 에는 내 로그인한 정보가 담김
            //authentication 객체가 session 영역에 저장됨 -> 로그인이 되었다는 것

            PrincipalDetails principalDetails = (PrincipalDetails) authentication.getPrincipal();
            System.out.println(principalDetails.getUser().getUsername());

            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("--------------------------------------------");
        //1. username.password 받아서
        //2. 정상인지 로그인 시도를 해보는 것 authenticationFilter 로 로그인 시도를 하면 PrincipalDetailService 가 호출됨
        //3. loadUserByUsername() 함수가 실행됨
        //4. PrincipalDetails를 세션에 담는다 (왜 세션이 담느냐. 세션에 값이 있어야 권한관리가 됨)
        //5. JWT 토큰을 만들어서 응답해주면 됨

        return null;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        PrincipalDetails principalDetails = (PrincipalDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("cos토큰") // 토큰 이름 설정
                .withExpiresAt(new Date(System.currentTimeMillis() + (1000 * 60 * 60 * 24)))
                .withClaim("id",principalDetails.getUser().getId())  //비공개 사항으로 내가 원하는 정보를 넣음 위드클레임
                .withClaim("username", principalDetails.getUser().getUsername())
                .sign(Algorithm.HMAC512("SECRET"));// 고유한 시크릿 값 적용
        response.addHeader("Authorization", "Bearer " + jwtToken);




    }
}
