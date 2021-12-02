package com.example.scrapingapp.service;

public interface AdminService {
    void deleteAllRestaurants();
    void deleteMealsByRestaurantId(String restaurantId);
}
