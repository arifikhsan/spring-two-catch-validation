package com.example.springtwocatchvalidation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class HelloController {
    @PostMapping
    public String hello(@Valid @RequestBody HelloRequestDto dto) {
        return dto.getMessage();
    }
}
