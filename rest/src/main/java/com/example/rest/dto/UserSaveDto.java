package com.example.rest.dto;

import com.example.common.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserSaveDto {

    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private UserType userType;

}
