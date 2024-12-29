package com.ecommerce.gadgetzone.service.interfaces;

import java.util.List;

import com.ecommerce.gadgetzone.dto.request.UserLogInRequest;
import com.ecommerce.gadgetzone.dto.request.UserSignUpRequest;
import com.ecommerce.gadgetzone.dto.response.UserLogInResponse;

import jakarta.servlet.http.HttpServletRequest;

public interface IUserService {

    void registerUser(UserSignUpRequest userSignUpRequest);
    
    UserLogInResponse logIn(UserLogInRequest userLogInRequest);

    UserLogInResponse getAuthenticatedUserResponse(String token);

    void logout(HttpServletRequest request);
    
    List<UserLogInResponse> getAllUsers();

    void deleteUserById(int userId);
}
