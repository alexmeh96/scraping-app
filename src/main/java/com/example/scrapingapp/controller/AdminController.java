package com.example.scrapingapp.controller;

import com.example.scrapingapp.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @DeleteMapping("/deleteAllRestaurant")
    public ResponseEntity<?> deleteAllRestaurant() {
        try {
            adminService.deleteAllRestaurants();
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/deleteMealsByRestaurantId/{restaurantId}")
    public ResponseEntity<?> deleteMealsByRestaurantId(@PathVariable String restaurantId) {
        try {
            adminService.deleteMealsByRestaurantId(restaurantId);
            return new ResponseEntity<>("Success", HttpStatus.OK);
        } catch (Exception e) {
            log.error(e.getMessage());
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}