package com.ecommerce.gadgetzone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserLogInResponse {
    private String email;
    private String role;
    private String token;
}
