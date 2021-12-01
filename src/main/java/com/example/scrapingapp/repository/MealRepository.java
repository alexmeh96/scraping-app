package com.example.scrapingapp.repository;

import com.example.scrapingapp.model.Meal;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface MealRepository extends MongoRepository<Meal, String> {
    List<Meal> findAllByRestaurantId(String id);
}
