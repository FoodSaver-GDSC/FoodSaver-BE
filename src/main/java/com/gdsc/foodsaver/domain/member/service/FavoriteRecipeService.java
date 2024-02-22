package com.gdsc.foodsaver.domain.member.service;

import com.gdsc.foodsaver.domain.member.entity.FavoriteRecipe;
import com.gdsc.foodsaver.domain.member.entity.Member;
import com.gdsc.foodsaver.domain.member.repository.FavoriteRecipeRepository;
import com.gdsc.foodsaver.domain.recipe.entity.Recipe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class FavoriteRecipeService {
    private final FavoriteRecipeRepository favoriteRecipeRepository;

    public List<Recipe> getFavoriteRecipes(Member member) {
        List<FavoriteRecipe> favoriteRecipes = favoriteRecipeRepository.findByMember(member);
        return favoriteRecipes.stream()
                .map(FavoriteRecipe::getRecipe)
                .collect(Collectors.toList());
    }

    // 회원이 레시피를 좋아하는지 여부 확인
    public boolean isRecipeFavorited(Member member, Recipe recipe) {
        return favoriteRecipeRepository.existsByMemberAndRecipe(member, recipe);
    }

    // 레시피를 좋아요 추가 또는 삭제
    public boolean toggleFavoriteRecipe(Member member, Recipe recipe) {
        if (isRecipeFavorited(member, recipe)) {
            // 이미 좋아요한 경우 좋아요 취소
            FavoriteRecipe favoriteRecipe = favoriteRecipeRepository.findByMemberAndRecipe(member, recipe)
                    .orElseThrow(() -> new RuntimeException("FavoriteRecipe not found"));
            favoriteRecipeRepository.delete(favoriteRecipe);
            log.info("레시피 저장 취소!!!");
            return false;
        } else {
            // 좋아요 추가
            FavoriteRecipe favoriteRecipe = new FavoriteRecipe();
            favoriteRecipe.setMember(member);
            favoriteRecipe.setRecipe(recipe);
            favoriteRecipeRepository.save(favoriteRecipe);
            log.info("레시피 저장!!!!");
            return true;
        }
    }
}
