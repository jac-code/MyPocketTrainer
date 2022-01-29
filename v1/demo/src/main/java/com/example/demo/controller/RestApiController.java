package com.example.demo.controller;

import com.example.demo.service.RecipeService;
import com.example.demo.service.TrainingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api")
public class RestApiController {

    @Autowired
    private RecipeService recipeService;

    @Autowired
    private TrainingService trainingService;


    @GetMapping("/recipes")
    public ResponseEntity<String> findRecipesByMaxCalories(@RequestParam("maxcalories") int maxcalories) {
        return new ResponseEntity<String>(recipeService.getRecipes(maxcalories), HttpStatus.OK);
    }

    @GetMapping("/recipe/{id}")
    public ResponseEntity<String> getRecipeLinkById(@PathVariable("id") String recipeId) {
        return new ResponseEntity<String>(recipeService.getRecipeLink(recipeId), HttpStatus.OK);
    }

    @GetMapping("/training/exercise")
    public ResponseEntity<String> findExercise() {
        return new ResponseEntity<String>(trainingService.getExercise(), HttpStatus.OK);
    }
}
