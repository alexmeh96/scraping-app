package com.example.scrapingapp.config;

import org.modelmapper.ModelMapper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


//    @Bean
//    public WebDriver webDriver() {
//        return new ChromeDriver();
//    }
}
