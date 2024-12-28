package com.ecommerce.gadgetzone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLogInResponse {
    private String email;
    private int userId;
    private String firstName;
    private String lastName;
    private String role;
    private String token;
}
