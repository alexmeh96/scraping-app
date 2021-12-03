package com.example.scrapingapp.service.impl;

import com.example.scrapingapp.service.ScrapingService;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class ScrapingSeleniumServiceImpl implements ScrapingService {

//    private final WebDriver driver;
//
//    public ScrapingSeleniumServiceImpl(WebDriver driver) {
//        this.driver = driver;
//    }


    @Override
    public void scraping() {
        System.setProperty("webdriver.gecko.driver", "/home/alex/work/settings/geckodriver");

        WebDriverManager.firefoxdriver().setup();

        WebDriver driver = new FirefoxDriver();

        driver.get("https://google.com");

        System.out.println("!!!");

    }
}
