package com.ecommerce.gadgetzone.service.interfaces;

import com.ecommerce.gadgetzone.dto.request.UserLogInRequest;
import com.ecommerce.gadgetzone.dto.request.UserSignUpRequest;
import com.ecommerce.gadgetzone.dto.response.UserLogInResponse;

public interface IUserService {

    void registerUser(UserSignUpRequest userSignUpRequest);
    
    UserLogInResponse logIn(UserLogInRequest userLogInRequest);
    
}
