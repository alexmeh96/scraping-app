package com.example.scrapingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RestaurantDto {
    private String id;
    private String name;
    private String address;
    private String img;
    private String description;
    private String[] kitchens;
    private String[] categories;

    private List<MealDto> meals = new ArrayList<>();
}
