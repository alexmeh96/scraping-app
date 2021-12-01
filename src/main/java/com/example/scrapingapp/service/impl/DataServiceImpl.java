package com.example.scrapingapp.service.impl;

import com.example.scrapingapp.exception.ResourceNotFoundException;
import com.example.scrapingapp.model.Meal;
import com.example.scrapingapp.model.Restaurant;
import com.example.scrapingapp.repository.MealRepository;
import com.example.scrapingapp.repository.RestaurantRepository;
import com.example.scrapingapp.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.scrapingapp.exception.ErrorMessage.NO_FOUND_BY_ID_ERROR;

@Slf4j
@Service
public class DataServiceImpl implements DataService {

    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;

    public DataServiceImpl(RestaurantRepository restaurantRepository, MealRepository mealRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
    }

    @Override
    public Restaurant getRestaurant(String id) {
        log.info("Get restaurants from db");
        return restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NO_FOUND_BY_ID_ERROR + id));
    }

    @Override
    public List<Restaurant> getRestaurants() {
        log.info("Get restaurant by id from db");
        return restaurantRepository.findAll();
    }

    @Override
    public Meal getMeal(String id) {
        log.info("Get meal by id from db");
        return mealRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(NO_FOUND_BY_ID_ERROR + id));
    }

    @Override
    public List<Meal> getMeals(String restaurantId) {
        log.info("Get meals by restaurantId from db");
        return mealRepository.findAllByRestaurantId(restaurantId);
    }
}
