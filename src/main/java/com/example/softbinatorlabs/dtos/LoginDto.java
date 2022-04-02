package com.example.softbinatorlabs.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class LoginDto {

    private String email;

    private String password;

    // Pentru login este "password"
    private String grantType;

}
