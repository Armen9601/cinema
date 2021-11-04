package com.example.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BasketDto {

    private List<String> myPlaces;
    private List<FoodDto> foods;
    private String seance;
    private int movieId;

}
