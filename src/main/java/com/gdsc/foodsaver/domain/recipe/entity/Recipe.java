package com.gdsc.foodsaver.domain.recipe.entity;

import com.gdsc.foodsaver.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String cookingMethod;
    private String dishType;
    private String servingSize;
    @Embedded
    private NutritionFacts nutritionFacts;
    private String hashtags;
    private String imageUrlSmall;
    private String imageUrlBig;
    @Column(length = 10000)
    private String ingredients;
    @Column(length = 10000)
    private String recipe;

    @Builder
    public Recipe(String name, String cookingMethod, String dishType, String servingSize,
                  Double calories, Double carbohydrates, Double protein, Double fat, Double sodium,
                  String hashtags, String imageUrlSmall, String imageUrlBig, String ingredients, String recipe) {

        NutritionFacts nutritionFacts1 = new NutritionFacts(calories, carbohydrates, protein, fat, sodium);

        this.name = name;
        this.cookingMethod = cookingMethod;
        this.dishType = dishType;
        this.servingSize = servingSize;
        this.nutritionFacts = nutritionFacts1;
        this.hashtags = hashtags;
        this.imageUrlSmall = imageUrlSmall;
        this.imageUrlBig = imageUrlBig;
        this.ingredients = ingredients;
        this.recipe = recipe;
    }
}

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
class NutritionFacts {
    private Double calories;
    private Double carbohydrates;
    private Double protein;
    private Double fat;
    private Double sodium;
}