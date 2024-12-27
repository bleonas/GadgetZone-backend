package com.ecommerce.gadgetzone.controller;

import com.ecommerce.gadgetzone.dto.request.UserSignUpRequest;
import com.ecommerce.gadgetzone.dto.response.UserLogInResponse;
import com.ecommerce.gadgetzone.dto.request.UserLogInRequest;
import com.ecommerce.gadgetzone.service.classes.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.ecommerce.gadgetzone.dto.request.UserSignUpRequest;
import com.ecommerce.gadgetzone.service.interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserSignUpRequest signUpRequest) {
        try {
            userService.registerUser(signUpRequest);
            return ResponseEntity.ok("Registration successful");
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Email is already taken");
        }
    }

    @CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true") 
     @PostMapping("/login")
    public ResponseEntity<UserLogInResponse> logIn(@RequestBody UserLogInRequest loginRequest) {
        UserLogInResponse response = userService.logIn(loginRequest);
        return ResponseEntity.ok(response);
    }

}
