package com.example.scrapingapp.controller;

import com.example.scrapingapp.dto.RestaurantDto;
import com.example.scrapingapp.model.Meal;
import com.example.scrapingapp.model.Restaurant;
import com.example.scrapingapp.service.DataService;
import lombok.extern.slf4j.Slf4j;
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

    public DataController(DataService dataService) {
        this.dataService = dataService;
    }

    @GetMapping("/restaurants")
    public ResponseEntity<?> restaurants() {
        try {

            List<Restaurant> restaurants = dataService.getRestaurants();

            List<RestaurantDto> restaurantDtoList = List.of(new RestaurantDto(), new RestaurantDto());
            return new ResponseEntity<>(restaurantDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/restaurant/{id}")
    public ResponseEntity<?> restaurant(@PathVariable String id) {
        try {

            Restaurant restaurant = dataService.getRestaurant(id);

            RestaurantDto restaurantDto = new RestaurantDto();
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

            List<RestaurantDto> restaurantDtoList = List.of(new RestaurantDto(), new RestaurantDto());
            return new ResponseEntity<>(restaurantDtoList, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/meal/{id}")
    public ResponseEntity<?> meal(@PathVariable String id) {
        try {

            Meal meal = dataService.getMeal(id);

            RestaurantDto restaurantDto = new RestaurantDto();
            return new ResponseEntity<>(restaurantDto, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}