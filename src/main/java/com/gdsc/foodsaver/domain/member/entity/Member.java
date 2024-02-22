package com.gdsc.foodsaver.domain.member.entity;

import com.gdsc.foodsaver.domain.recipe.entity.Recipe;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String oauthId;
    private String email;
    @Value("${password}")
    private String password;
    private String type;
    private String role;

    public Member (String oauthId, String email, String type) {
        this.oauthId = oauthId;
        this.email = email;
        this.type = type;
        this.role = "ROLE_USER";
    }
}
