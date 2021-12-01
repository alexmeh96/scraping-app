package com.example.scrapingapp.service;


import com.example.scrapingapp.model.Meal;
import com.example.scrapingapp.model.Restaurant;

import java.util.List;

public interface DataService {
    Restaurant getRestaurant(String id);
    List<Restaurant> getRestaurants();
    Meal getMeal(String id);
    List<Meal> getMeals(String restaurantId);
}
