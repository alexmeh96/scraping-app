package com.example.scrapingapp.controller;

import com.example.scrapingapp.dto.UrlRestaurantDto;
import com.example.scrapingapp.model.UrlRestaurant;
import com.example.scrapingapp.service.UrlRestaurantService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/url")
public class UrlRestaurantController {

    private final UrlRestaurantService urlRestaurantService;
    private final ModelMapper modelMapper;


    public UrlRestaurantController(UrlRestaurantService urlRestaurantService, ModelMapper modelMapper) {
        this.urlRestaurantService = urlRestaurantService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/getUrlRestaurants")
    public ResponseEntity<?> getUrlRestaurants() {
        try {
            List<UrlRestaurant> urlRestaurants = urlRestaurantService.getUrlRestaurants();
            UrlRestaurantDto[] UrlRestaurantDtos = modelMapper.map(urlRestaurants, UrlRestaurantDto[].class);

            return new ResponseEntity<>(UrlRestaurantDtos, HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addUrlRestaurant")
    public ResponseEntity<?> getUrlRestaurants(@RequestBody UrlRestaurantDto urlRestaurantDto) {
        try {
            urlRestaurantService.addUrl(urlRestaurantDto.getUrl());
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
