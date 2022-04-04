package com.example.softbinatorlabs.dtos;
import com.example.softbinatorlabs.models.Donation;
import com.example.softbinatorlabs.models.Wallet;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserInfoDto {
    private Long id;

    private String username;

    private String email;

    @JsonIgnoreProperties(value = {"user"})
    private Wallet wallet;

    @JsonIgnoreProperties(value = {"event", "user"})
    private List<Donation> donations;

}