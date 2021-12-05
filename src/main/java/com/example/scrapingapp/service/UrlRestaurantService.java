package com.example.scrapingapp.service;

import com.example.scrapingapp.model.UrlRestaurant;
import org.springframework.stereotype.Service;

import java.util.List;

public interface UrlRestaurantService {
    void addUrl(String url);

    void addUrl(String url, String address);
    void addUrl(String url, String address, String deliver);
    List<UrlRestaurant> getUrlRestaurants();

    void deleteUrlById(String id);
}
