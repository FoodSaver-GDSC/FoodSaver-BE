package com.gdsc.foodsaver.domain.member.repository;

import com.gdsc.foodsaver.domain.member.entity.FavoriteRecipe;
import com.gdsc.foodsaver.domain.member.entity.Member;
import com.gdsc.foodsaver.domain.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface FavoriteRecipeRepository extends JpaRepository<FavoriteRecipe, Long> {

    List<FavoriteRecipe> findByMember(Member member);

    boolean existsByMemberAndRecipe(Member member, Recipe recipe);

    Optional<FavoriteRecipe> findByMemberAndRecipe(Member member, Recipe recipe);

}
