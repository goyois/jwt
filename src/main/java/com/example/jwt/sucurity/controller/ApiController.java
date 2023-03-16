package com.example.jwt.sucurity.controller;


import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {

    @GetMapping
    public String home() {
        return "<h1>home<h1>";
    }


    @PostMapping("token")
    public String token() {
        return "<h1>token<h1>";
    }
}
