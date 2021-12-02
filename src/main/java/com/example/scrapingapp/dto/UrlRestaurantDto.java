package com.example.scrapingapp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UrlRestaurantDto {
    private String id;

    private String url;
    private String address;
    private String deliver;
    private String name;
}
