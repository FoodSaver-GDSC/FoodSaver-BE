package com.gdsc.foodsaver.domain.member.controller;

import com.gdsc.foodsaver.domain.member.entity.Member;
import com.gdsc.foodsaver.domain.member.service.FavoriteRecipeService;
import com.gdsc.foodsaver.domain.member.service.MemberService;
import com.gdsc.foodsaver.domain.recipe.entity.Recipe;
import com.gdsc.foodsaver.domain.recipe.service.RecipeService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    private final FavoriteRecipeService favoriteRecipeService;
    private final RecipeService recipeService;

    @GetMapping
    public Member retrieveMember(HttpServletRequest request) {
        String oauthId = memberService.retrieveName(request);
        Member member = memberService.findMemberByOauthId(oauthId);

        return member;
    }

    @GetMapping("/my-recipe")
    public List<Recipe> retrieveMyRecipe(HttpServletRequest request) {
        String oauthId = memberService.retrieveName(request);
        Member member = memberService.findMemberByOauthId(oauthId);

        return favoriteRecipeService.getFavoriteRecipes(member);
    }

    @GetMapping("/toogle-recipe")
    public String toogleMyRecipe(HttpServletRequest request, @RequestParam Long recipeId) {
        String oauthId = memberService.retrieveName(request);
        Member member = memberService.findMemberByOauthId(oauthId);

        Recipe recipe = recipeService.retrieveRecipe(recipeId);

        boolean yn = favoriteRecipeService.toggleFavoriteRecipe(member, recipe);
        if(yn)
            return "레시피 저장";
        else
            return "레시피 저장취소";
    }

}
