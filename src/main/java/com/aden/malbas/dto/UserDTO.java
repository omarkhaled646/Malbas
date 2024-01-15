package com.aden.malbas.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String gender;
    private String size;
    private String role;

}
