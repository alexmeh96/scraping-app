package com.example.scrapingapp.service.impl;

import org.apache.commons.io.FileUtils;
import com.example.scrapingapp.model.Meal;
import com.example.scrapingapp.model.Restaurant;
import com.example.scrapingapp.model.UrlRestaurant;
import com.example.scrapingapp.repository.MealRepository;
import com.example.scrapingapp.repository.RestaurantRepository;
import com.example.scrapingapp.repository.UrlRestaurantRepository;
import com.example.scrapingapp.service.ScrapingService;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Primary
@Service
public class ScrapingSeleniumServiceImpl implements ScrapingService {

    private final RestaurantRepository restaurantRepository;
    private final MealRepository mealRepository;
    private final UrlRestaurantRepository urlRestaurantRepository;

    public ScrapingSeleniumServiceImpl(RestaurantRepository restaurantRepository, MealRepository mealRepository, UrlRestaurantRepository urlRestaurantRepository) {
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

        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        chromeOptions.addArguments("--headless");
        chromeOptions.addArguments("start-maximized");

        log.info("Open webdriver");
        WebDriver driver = null;

        for (UrlRestaurant urlRestaurant : urlRestaurants) {
            try {
                driver = new ChromeDriver(chromeOptions);
                log.info("Open url: {}", urlRestaurant.getUrl());
//                openRestaurant(driver, "https://www.delivery-club.ru/srv/Tjerjemok_spb", "Санкт-Петербург, Новосмоленская набережная, 1");
                openRestaurant(driver, urlRestaurant.getUrl(), urlRestaurant.getAddress());
                Thread.sleep(5000);
                WebElement foo = new WebDriverWait(driver, Duration.ofSeconds(30).getSeconds())
                        .until(dr -> dr.findElement(By.className("menu-product")));

                // скриншот ресторана
                File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(scrFile, new File("./restaurant.png"));

                scrapRestaurant(driver, urlRestaurant.getUrl());
            } catch (Exception e) {
                e.printStackTrace();
                log.error("Scrapping url: {}", urlRestaurant.getUrl());
                try {
                    // скриншот ошибки
                    File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
                    FileUtils.copyFile(scrFile, new File("./error.png"));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            } finally {
                log.info("Close webdriver");
                driver.close();
            }
        }

        driver.quit();


        System.out.println("!!!");
    }

    private void scrapRestaurant(WebDriver driver, String url) {

        log.info("Start scraping url: {}", url);

        String nameRestaurant = driver.findElement(By.className("vendor-headline__title")).getText().strip();
        String describeRestaurant = driver.findElement(By.className("vendor-information__paragraph")).getText().strip();
        String[] kitchens = driver.findElement(By.className("vendor-information__col")).getText().replace("Специализация", "").strip().split(", ");
        String address = driver.findElement(By.className("vendor-information__affiliate-item")).getText().strip();

        List<WebElement> webElements = driver.findElements(By.className("vendor-menu__section"));

        List<String> categories = new ArrayList<>();
        List<Meal> meals = new ArrayList<>();

        Actions actionProvider = new Actions(driver);

        int count = 0;
        System.out.println(webElements.size());

        List<WebElement> dialogs = driver.findElements(By.className("popup__container"));

        if (dialogs.size() != 0) {
            log.info("Close dialog window:");
            dialogs.get(0).findElement(By.className("icon__container")).click();
        }

        for (WebElement element : webElements) {
            String category = element.findElement(By.className("vendor-menu__category-title")).getText().strip();
            categories.add(category);
            System.out.println(category);
            List<WebElement> elements = element.findElements(By.className("menu-product"));
            System.out.println(elements.size());

            for (WebElement element1 : elements) {
                count++;
                String img = element1.findElement(By.tagName("div")).getAttribute("data-src");
                img = img.split("\\?")[0];

                String name = element1.findElement(By.className("menu-product__title")).getText().strip();
                String price = element1.findElement(By.className("menu-product__price")).getText().strip().split(" ")[0];

                actionProvider.moveToElement(element1).build().perform();
                WebElement foo = new WebDriverWait(driver, Duration.ofSeconds(30).getSeconds())
                        .until(dr -> dr.findElement(By.className("menu-product__info-container--hover")));
                String description = driver.findElement(By.className("menu-product__info-container--hover"))
                        .findElement(By.className("menu-product__info-block--hover"))
                        .findElement(By.className("menu-product__description"))
                        .getText().strip();

                Meal meal = Meal.builder()
                        .name(name)
                        .description(description)
                        .price(Integer.parseInt(price))
                        .img(img)
                        .category(category)
                        .build();

                meals.add(meal);
                System.out.println(count);
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

    }

    private void openRestaurant(WebDriver driver, String url, String address) throws InterruptedException {
        driver.get(url);
        WebElement foo = new WebDriverWait(driver, Duration.ofSeconds(30).getSeconds())
                .until(dr -> dr.findElement(By.className("address-input__container")));

        foo.click();

        WebElement input = new WebDriverWait(driver, Duration.ofSeconds(30).getSeconds())
                .until(dr -> dr.findElement(By.tagName("input")));

        input.sendKeys(address);

//        System.out.println(foo2.getAttribute("innerHTML"));

        WebElement div = new WebDriverWait(driver, Duration.ofSeconds(30).getSeconds())
                .until(dr -> dr.findElement(By.className("address-suggest__wrapper--at-header"))
                        .findElement(By.id("address-suggest-item-0")));

        System.out.println(div.getText());
        div.click();
    }

}
