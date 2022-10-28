package com.example.twiter.controller;


import com.example.twiter.service.UserSerivce;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserSerivce userSerivce;

    @PostMapping("/signup")
    public ResponseDto<?> signup(@RequestBody UserRequestDto userRequestDto) {
        return userService.signup(userRequestDto);
    }


}
