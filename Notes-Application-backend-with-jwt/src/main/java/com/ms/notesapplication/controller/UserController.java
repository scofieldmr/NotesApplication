package com.ms.notesapplication.controller;

import com.ms.notesapplication.dto.LoginJwtResponse;
import com.ms.notesapplication.dto.LoginRequest;
import com.ms.notesapplication.dto.SignupRequest;
import com.ms.notesapplication.dto.SignupResponse;
import com.ms.notesapplication.service.CustomUserDetailsService;
import com.ms.notesapplication.service.UserService;
import jakarta.validation.groups.Default;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody @Validated({Default.class})  SignupRequest signupRequest) {
        SignupResponse newUserSignup = userService.userSignup(signupRequest);
        return new ResponseEntity<>(newUserSignup, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginJwtResponse> login(@RequestBody @Validated({Default.class}) LoginRequest loginRequest) {
        LoginJwtResponse loginJwtResponse = userService.userLogin(loginRequest);
        return new ResponseEntity<>(loginJwtResponse, HttpStatus.OK);
    }

    @PostMapping("/jwtlogin")
    public ResponseEntity<LoginJwtResponse> loginJwt(@RequestBody @Validated({Default.class})  LoginRequest loginRequest) {
        LoginJwtResponse loginJwtResponse = userService.userJwtLogin(loginRequest);

        return new ResponseEntity<>(loginJwtResponse, HttpStatus.OK);
    }

}
