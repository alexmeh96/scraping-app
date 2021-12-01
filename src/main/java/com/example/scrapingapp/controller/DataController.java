package com.example.scrapingapp.controller;

import com.example.scrapingapp.dto.MealDto;
import com.example.scrapingapp.dto.RestaurantDto;
import com.example.scrapingapp.model.Meal;
import com.example.scrapingapp.model.Restaurant;
import com.example.scrapingapp.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.mapper.Mapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/data")
public class DataController {

    private final DataService dataService;

    private final ModelMapper modelMapper;

    public DataController(DataService dataService, ModelMapper modelMapper) {
        this.dataService = dataService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/restaurants")
    public ResponseEntity<?> restaurants() {
        try {

            List<Restaurant> restaurants = dataService.getRestaurants();
            RestaurantDto[] restaurantDtos = modelMapper.map(restaurants, RestaurantDto[].class);

            return new ResponseEntity<>(restaurantDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> restaurant(@PathVariable String id) {
        try {

            Restaurant restaurant = dataService.getRestaurant(id);
            RestaurantDto restaurantDto = modelMapper.map(restaurant, RestaurantDto.class);

            return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/meals/{restaurantId}")
    public ResponseEntity<?> meals(@PathVariable String restaurantId) {
        try {

            List<Meal> meals = dataService.getMeals(restaurantId);
            MealDto[] mealDtos = modelMapper.map(meals, MealDto[].class);

            return new ResponseEntity<>(mealDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/meal/{id}")
    public ResponseEntity<?> meal(@PathVariable String id) {
        try {

            Meal meal = dataService.getMeal(id);
            MealDto mealDto = modelMapper.map(meal, MealDto.class);

            return new ResponseEntity<>(mealDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}