package com.example.scrapingapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "meals")
public class Meal {

    @Id
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
