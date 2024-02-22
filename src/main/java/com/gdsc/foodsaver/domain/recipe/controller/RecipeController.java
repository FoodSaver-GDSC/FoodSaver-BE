package com.gdsc.foodsaver.domain.recipe.controller;

import com.gdsc.foodsaver.domain.member.entity.Member;
import com.gdsc.foodsaver.domain.member.service.MemberService;
import com.gdsc.foodsaver.domain.recipe.entity.Recipe;
import com.gdsc.foodsaver.domain.recipe.service.RecipeCsvDataLoaderService;
import com.gdsc.foodsaver.domain.recipe.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/recipes")
public class RecipeController {
    private final RecipeService recipeService;
    private final RecipeCsvDataLoaderService recipeCsvDataLoaderService;
    private final MemberService memberService;

    @Value("${recipe-csv}")
    private String recipeCsv;
    @Value("${recipe-deploy-csv}")
    private String recipeDeployCsv;

    @GetMapping
    public List<Recipe> retrieveAll() {
        return recipeService.retrieveAll();
    }

    @GetMapping("/name")
    public List<Map<String, Object>> retrieveAllNameAndId() {
        return recipeService.retrieveAllNameAndId();
    }

    @GetMapping("/{id}")
    public Recipe retrieveRecipe(@PathVariable Long id) {
        Recipe recipe = recipeService.retrieveRecipe(id);

        return recipe;
    }

    @GetMapping("/filter")
    public ResponseEntity<List<Recipe>> filterRecipes(@RequestParam List<String> ingredientNames) {
        if (ingredientNames.isEmpty()) {
            return ResponseEntity.badRequest().body(Collections.emptyList());
        }

        List<Recipe> filteredRecipes = recipeService.filterRecipesByIngredients(ingredientNames);
        if (filteredRecipes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // 랜덤으로 순서를 섞음
        List<Recipe> shuffledRecipes = new ArrayList<>(filteredRecipes);
        Collections.shuffle(shuffledRecipes);

        List<Recipe> top5Recipes = shuffledRecipes.stream().limit(5).collect(Collectors.toList());

        // 필터된 레시피 중에서 일부를 반환하거나, 모든 레시피를 반환하도록 필요에 따라 조정
        return ResponseEntity.ok(top5Recipes);
    }

//    ///////////////////////////////////////////////////
//    @GetMapping("/load-data")
//    public String saveAllData() {
//        recipeCsvDataLoaderService.loadCsvData(recipeCsv);
////        recipeCsvDataLoaderService.loadCsvData(recipeDeployCsv);
//        return "Data load Done. - Recipe";
//    }
}
