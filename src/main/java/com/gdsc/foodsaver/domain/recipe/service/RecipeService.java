package com.gdsc.foodsaver.domain.recipe.service;

import com.gdsc.foodsaver.domain.member.handler.MemberHandler;
import com.gdsc.foodsaver.domain.recipe.entity.Recipe;
import com.gdsc.foodsaver.domain.recipe.repository.RecipeRepository;
import com.gdsc.foodsaver.global.common.ResponseCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RecipeService {
    private final RecipeRepository recipeRepository;

    public List<Recipe> retrieveAll() {
        return recipeRepository.findAll();
    }

    public List<Map<String, Object>> retrieveAllNameAndId() {
        List<Recipe> recipes = retrieveAll();

        // Recipe 객체에서 이름만 추출하여 List<String>으로 변환
        return recipes.stream()
                .map(recipe -> {
                    Map<String, Object> recipeInfo = new HashMap<>();
                    recipeInfo.put("id", recipe.getId());
                    recipeInfo.put("name", recipe.getName());
                    return recipeInfo;
                })
                .collect(Collectors.toList());
    }

    public Recipe retrieveRecipe(Long id) {
        return recipeRepository.findById(id).orElseThrow(() -> new MemberHandler(ResponseCode.RECIPE_NOT_FOUND));
    }

    public List<Recipe> filterRecipesByIngredients(List<String> ingredientNames) {
        // 각 재료에 대해 레시피를 찾아올 임시 변수
        List<Recipe> resultRecipes = null;

        // 각 재료에 대해 레시피를 찾아온 후, 결과를 합칠 Set
        Set<Recipe> intersection = new HashSet<>();

        // 첫 번째 재료에 대해 레시피를 찾아옴
        if (!ingredientNames.isEmpty()) {
            resultRecipes = recipeRepository.findByIngredientsContainingIgnoreCase(ingredientNames.get(0));
            intersection.addAll(resultRecipes);
        }

        // 나머지 재료에 대해 레시피를 찾아온 후, 결과와의 교집합을 구함
        for (int i = 1; i < ingredientNames.size(); i++) {
            resultRecipes = recipeRepository.findByIngredientsContainingIgnoreCase(ingredientNames.get(i));
            intersection.retainAll(resultRecipes);
        }

        // Set을 List로 변환하여 반환
        return List.copyOf(intersection);
    }
}
