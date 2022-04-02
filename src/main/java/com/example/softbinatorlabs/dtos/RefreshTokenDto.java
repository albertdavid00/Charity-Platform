package com.example.softbinatorlabs.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenDto {

    @NotNull
    private String refreshToken;

    // pentru refreshToken este refresh_token
    @NotNull
    private String grantType;
}
