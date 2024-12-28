package com.ecommerce.gadgetzone.service.classes;

import com.ecommerce.gadgetzone.config.JwtService;
import com.ecommerce.gadgetzone.dto.request.UserLogInRequest;
import com.ecommerce.gadgetzone.dto.request.UserSignUpRequest;
import com.ecommerce.gadgetzone.dto.response.UserLogInResponse;
import com.ecommerce.gadgetzone.entity.User; 
import com.ecommerce.gadgetzone.repository.UserRepository; 
import com.ecommerce.gadgetzone.service.interfaces.IUserService;

import jakarta.servlet.http.HttpServletRequest;

import com.ecommerce.gadgetzone.enums.Role;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;


@Service
@CrossOrigin
@RequiredArgsConstructor
public class UserService implements IUserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    
    @Override
    public void registerUser(UserSignUpRequest userSignUpRequest) {
        Optional<User> existingUser = userRepository.findByEmail(userSignUpRequest.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("Email already taken");
        }

        User user = User.builder()
                .email(userSignUpRequest.getEmail())
                .password(passwordEncoder.encode(userSignUpRequest.getPassword())) 
                .firstName(userSignUpRequest.getFirstName())
                .lastName(userSignUpRequest.getLastName())
                .role(Role.USER)
                .build();

        userRepository.save(user);
    }

    
    public UserLogInResponse logIn(UserLogInRequest userLogInRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    userLogInRequest.getEmail(),
                    userLogInRequest.getPassword()
                )
        );

        User user = userRepository.findByEmail(userLogInRequest.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username or password"));

        String jwtToken = jwtService.generateToken(user);

        return new UserLogInResponse(user.getEmail(), user.getRole().name(), jwtToken);
    }

    public User getAuthenticatedUser() {
        String authenticatedUserEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(authenticatedUserEmail)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public UserLogInResponse getAuthenticatedUserResponse(String token) {
        User authenticatedUser = getAuthenticatedUser();

        String role = authenticatedUser.getRole().name();

        UserLogInResponse userProfileResponse = new UserLogInResponse(
                authenticatedUser.getEmail(),
                role,
                token 
        );
        return userProfileResponse;
    }


    public void logout(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid or missing Authorization header");
        }

        String token = authorizationHeader.substring(7);

        jwtService.addToBlacklist(token);
    }
}


