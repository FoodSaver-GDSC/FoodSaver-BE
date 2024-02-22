package com.gdsc.foodsaver.domain.recipe.repository;

import com.gdsc.foodsaver.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    List<Recipe> findByIngredientsContainingIgnoreCase(String ingredients);
}
