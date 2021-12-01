package com.example.scrapingapp.service.impl;

import com.example.scrapingapp.service.ScrapingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ScrapingServiceImpl implements ScrapingService {

    @Override
    public void scraping() {
        log.info("Scrapping!!!");
    }
}
