package com.example.scrapingapp.repository;

import com.example.scrapingapp.model.UrlRestaurant;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UrlRestaurantRepository extends MongoRepository<UrlRestaurant, String> {
}
