package com.example.scrapingapp;

import com.example.scrapingapp.model.Meal;
import com.example.scrapingapp.model.Restaurant;
import com.example.scrapingapp.repository.MealRepository;
import com.example.scrapingapp.repository.RestaurantRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.List;

@SpringBootApplication
@EnableScheduling
public class ScrapingAppApplication implements CommandLineRunner {

    public ScrapingAppApplication(RestaurantRepository restaurantRepository, MealRepository mealRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
    }

    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;

    public static void main(String[] args) {
        SpringApplication.run(ScrapingAppApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
//        initDB();
    }

    private void initDB() {
        Restaurant restaurant1 = Restaurant.builder()
                .name("restaurant1").build();

        Restaurant restaurant2 = Restaurant.builder()
                .name("restaurant2").build();

        Meal meal1 = Meal.builder()
                .name("meal1")
                .category("category1")
                .restaurant("restaurant1")
                .build();

        Meal meal2 = Meal.builder()
                .name("meal2")
                .category("category2")
                .restaurant("restaurant1")
                .build();

        Meal meal3 = Meal.builder()
                .name("meal3")
                .category("category3")
                .restaurant("restaurant3")
                .build();

        Meal meal4 = Meal.builder()
                .name("meal4")
                .category("category4")
                .restaurant("restaurant4")
                .build();


        Restaurant restaurant = restaurantRepository.save(restaurant1);
        meal1.setRestaurantId(restaurant.getId());
        meal2.setRestaurantId(restaurant.getId());

        restaurant = restaurantRepository.save(restaurant2);
        meal3.setRestaurantId(restaurant.getId());
        meal4.setRestaurantId(restaurant.getId());


        List<Meal> meals = List.of(meal1, meal2, meal3, meal4);

        mealRepository.saveAll(meals);
    }
}
