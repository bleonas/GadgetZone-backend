package com.ecommerce.gadgetzone.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserLogInResponse {
    private String email;
    private int userId;
    private String firstName;
    private String lastName;
    private String role;
    private String token;
}
