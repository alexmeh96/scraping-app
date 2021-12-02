package com.example.scrapingapp.service.impl;
import com.example.scrapingapp.repository.MealRepository;
import com.example.scrapingapp.repository.RestaurantRepository;
import com.example.scrapingapp.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class AdminServiceImpl implements AdminService {

    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;

    public AdminServiceImpl(RestaurantRepository restaurantRepository, MealRepository mealRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
    }

    @Override
    @Transactional
    public void deleteAllRestaurants() {
        log.info("Delete all restaurants from db");
        restaurantRepository.deleteAll();
        log.info("Delete all meals from db");
        mealRepository.deleteAll();
    }

    @Override
    public void deleteMealsByRestaurantId(String restaurantId) {
        log.info("Delete all meals from db by restaurantId: {}", restaurantId);
        mealRepository.deleteAllByRestaurantId(restaurantId);
    }
}
