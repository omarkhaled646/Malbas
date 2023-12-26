package com.aden.malbas.request;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class RegisterRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String size;
    private String role;
}
