package com.gdsc.foodsaver.domain.member.entity;

import com.gdsc.foodsaver.domain.recipe.entity.Recipe;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FavoriteRecipe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipe_id")
    private Recipe recipe;
}
