package com.example.scrapingapp.repository;

import com.example.scrapingapp.model.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    void deleteByUrl(String url);
    Restaurant findByUrl(String url);
}
