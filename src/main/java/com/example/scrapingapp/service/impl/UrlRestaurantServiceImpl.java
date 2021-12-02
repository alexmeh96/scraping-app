package com.example.scrapingapp.service.impl;

import com.example.scrapingapp.model.UrlRestaurant;
import com.example.scrapingapp.repository.UrlRestaurantRepository;
import com.example.scrapingapp.service.UrlRestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UrlRestaurantServiceImpl implements UrlRestaurantService {

    private final UrlRestaurantRepository urlRestaurantRepository;

    public UrlRestaurantServiceImpl(UrlRestaurantRepository urlRestaurantRepository) {
        this.urlRestaurantRepository = urlRestaurantRepository;
    }

    @Override
    public void addUrl(String url) {
        UrlRestaurant urlRestaurant = UrlRestaurant.builder()
                .url(url)
                .build();
        urlRestaurantRepository.save(urlRestaurant);
    }

    @Override
    public void addUrl(String url, String address) {
        UrlRestaurant urlRestaurant = UrlRestaurant.builder()
                .url(url)
                .address(address)
                .build();
        urlRestaurantRepository.save(urlRestaurant);
    }

    @Override
    public void addUrl(String url, String address, String name) {
        UrlRestaurant urlRestaurant = UrlRestaurant.builder()
                .url(url)
                .address(address)
                .name(name)
                .build();
        urlRestaurantRepository.save(urlRestaurant);
    }

    @Override
    public List<UrlRestaurant> getUrlRestaurants() {
        return urlRestaurantRepository.findAll();
    }
}
