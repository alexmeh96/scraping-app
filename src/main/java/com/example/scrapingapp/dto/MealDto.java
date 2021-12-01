package com.example.scrapingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MealDto {
    private String id;

    private String name;
    private String img;
    private String restaurantId;
    private String restaurant;
    private Integer price;
    private String description;
    private String category;
    private Integer weight;
    private Integer kcal;
}
