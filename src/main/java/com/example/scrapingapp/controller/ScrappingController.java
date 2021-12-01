package com.example.scrapingapp.controller;

import com.example.scrapingapp.dto.RestaurantDto;
import com.example.scrapingapp.model.Restaurant;
import com.example.scrapingapp.service.ScrapingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("scraping")
public class ScrappingController {

    private final ScrapingService scrapingService;

    public ScrappingController(ScrapingService scrapingService) {
        this.scrapingService = scrapingService;
    }

    @GetMapping
    public void scraping() {
        scrapingService.scraping();
    }

}
