package com.example.scrapingapp.service.impl;

import com.example.scrapingapp.model.Restaurant;
import com.example.scrapingapp.service.ScrapingService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
public class ScrapingServiceImpl implements ScrapingService {

    @Override
//    @Scheduled(fixedDelay = 3000, initialDelay = 1000)
    public void scraping() {
        log.info("Scrapping!!!");

        try {
            Document doc = Jsoup.connect("https://www.delivery-club.ru/srv/Lafucina").get();

            String nameRestaurant = doc.getElementsByClass("vendor-headline__title").text().strip();
            String describeRestaurant = doc.getElementsByClass("vendor-information__paragraph").text().strip();
            String[] kitchens = doc.getElementsByClass("vendor-information__col").first().text().replace("Специализация", "").strip().split(", ");
            String address = doc.getElementsByClass("vendor-information__affiliate-item").text().strip();




            Elements elements = doc.getElementsByClass("vendor-menu__section");


            List<String> categories = new ArrayList<>();

            for (Element element: elements) {
                String category = element.getElementsByClass("vendor-menu__category-title").text().strip();
                categories.add(category);

                Elements elements1 = element.getElementsByClass("menu-product");

                for (Element element1: elements1) {
                    String img = element1.getElementsByTag("div").attr("data-src");
                    img = img.split("\\?")[0];
                    System.out.println(img);
                }

            }

            Restaurant restaurant = Restaurant.builder()
                    .name(nameRestaurant)
                    .kitchens(List.of(kitchens))
                    .categories(categories)
                    .address(address)
                    .description(describeRestaurant)
                    .build();

            System.out.println(restaurant);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
