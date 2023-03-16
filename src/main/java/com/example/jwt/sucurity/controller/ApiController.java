package com.example.jwt.sucurity.controller;


import com.example.jwt.user.entity.User;
import com.example.jwt.user.entity.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class ApiController {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;
    @GetMapping
    public String home() {
        return "<h1>home<h1>";
    }


    @PostMapping("token")
    public String token() {
        return "<h1>token<h1>";
    }

    @PostMapping("join")
    public String join(@RequestBody User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles("USER_ROLE");
        userRepository.save(user);
        return  "회원가입 완료";

    }
}
