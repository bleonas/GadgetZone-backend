package com.ecommerce.gadgetzone.service;

import com.ecommerce.gadgetzone.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.CrossOrigin;

@Service
@CrossOrigin
public class UserService {

    @Autowired
    private UserRepository userRepository;

}
