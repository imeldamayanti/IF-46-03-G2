package com.tubes.controller;

import com.tubes.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/recommendations/{userId}")
    public Object getRecommendations(@PathVariable int userId) {
        return recommendationService.getRecommendationsForUser(userId);
    }
}
