package com.example.scrapingapp.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "url_restaurants")
public class UrlRestaurant {

    @Id
    private String id;

    private String url;
    private String address;
    private String deliver;
    private String name;
}
