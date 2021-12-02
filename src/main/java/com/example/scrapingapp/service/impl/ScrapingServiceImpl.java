package com.example.scrapingapp.service.impl;

import com.example.scrapingapp.model.Meal;
import com.example.scrapingapp.model.Restaurant;
import com.example.scrapingapp.model.UrlRestaurant;
import com.example.scrapingapp.repository.MealRepository;
import com.example.scrapingapp.repository.RestaurantRepository;
import com.example.scrapingapp.repository.UrlRestaurantRepository;
import com.example.scrapingapp.service.ScrapingService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class ScrapingServiceImpl implements ScrapingService {

    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;
    private final UrlRestaurantRepository urlRestaurantRepository;

    public ScrapingServiceImpl(RestaurantRepository restaurantRepository, MealRepository mealRepository, UrlRestaurantRepository urlRestaurantRepository) {
        this.restaurantRepository = restaurantRepository;
        this.mealRepository = mealRepository;
        this.urlRestaurantRepository = urlRestaurantRepository;
    }

    @Override
//    @Scheduled(fixedDelay = 10000, initialDelay = 1000)
    public void scraping() {
        log.info("Start scrapping");

        log.info("Get urlRestaurants from db");
        List<UrlRestaurant> urlRestaurants = urlRestaurantRepository.findAll();

        urlRestaurants.forEach(urlRestaurant -> {
//            scrapRestaurant("https://www.delivery-club.ru/srv/Lafucina");
            scrapRestaurant(urlRestaurant.getUrl());
        });



    }

    private void scrapRestaurant(String url) {
        try {

            log.info("Start scraping url: {}", url);
            Document doc = Jsoup.connect(url).get();

            String nameRestaurant = doc.getElementsByClass("vendor-headline__title").text().strip();
            String describeRestaurant = doc.getElementsByClass("vendor-information__paragraph").text().strip();
            String[] kitchens = doc.getElementsByClass("vendor-information__col").first().text().replace("Специализация", "").strip().split(", ");
            String address = doc.getElementsByClass("vendor-information__affiliate-item").text().strip();

            Elements elements = doc.getElementsByClass("vendor-menu__section");

            List<String> categories = new ArrayList<>();
            List<Meal> meals = new ArrayList<>();

            for (Element element: elements) {
                String category = element.getElementsByClass("vendor-menu__category-title").text().strip();
                categories.add(category);

                Elements elements1 = element.getElementsByClass("menu-product");

                for (Element element1: elements1) {
                    String img = element1.getElementsByTag("div").attr("data-src");
                    img = img.split("\\?")[0];

                    String name = element1.getElementsByClass("menu-product__title").text().strip();
                    String price = element1.getElementsByClass("menu-product__price").text().strip().split(" ")[0];

                    Meal meal = Meal.builder()
                            .name(name)
                            .price(Integer.parseInt(price))
                            .img(img)
                            .category(category)
                            .build();

                    meals.add(meal);
                }
            }

            Restaurant restaurant = Restaurant.builder()
                    .url(url)
                    .name(nameRestaurant)
                    .kitchens(List.of(kitchens))
                    .categories(categories)
                    .address(address)
                    .description(describeRestaurant)
                    .countMeals(meals.size())
                    .build();

            Restaurant restaurant1 = restaurantRepository.findByUrl(url);

            if (restaurant1 != null) {
                log.info("Delete restaurant from db by id: {}", restaurant1.getId());
                restaurantRepository.deleteById(restaurant1.getId());
                log.info("Delete meals of restaurant from db by restaurantId: {}", restaurant1.getId());
                mealRepository.deleteAllByRestaurantId(restaurant1.getId());
            }


            log.info("Save restaurant to db: {}", restaurant.getName());
            Restaurant saveRestaurant = restaurantRepository.save(restaurant);

            meals.forEach(meal -> {
                meal.setRestaurant(saveRestaurant.getName());
                meal.setRestaurantId(saveRestaurant.getId());
            });

            log.info("Save meals of restaurant to db: {}", restaurant.getName());
            mealRepository.saveAll(meals);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
