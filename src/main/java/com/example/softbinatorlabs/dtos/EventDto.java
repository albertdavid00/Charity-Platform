package com.example.softbinatorlabs.dtos;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class EventDto {

    private String title;

    private String description;

    private Double currentAmount;

    private Double targetAmount;
}
