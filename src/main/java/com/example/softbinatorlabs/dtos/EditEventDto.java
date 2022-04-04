package com.example.softbinatorlabs.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EditEventDto {

    private String title;

    private String description;

    private Double targetAmount;
}
