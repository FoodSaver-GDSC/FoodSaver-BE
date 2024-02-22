package com.gdsc.foodsaver.global.auth.handler;

import com.gdsc.foodsaver.domain.member.entity.CustomOAuth2Member;
import com.gdsc.foodsaver.global.auth.jwt.JwtProvider;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    private final JwtProvider jwtProvider;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) throws IOException, ServletException {
        CustomOAuth2Member oAuth2Member = (CustomOAuth2Member) authentication.getPrincipal();

        System.out.println(oAuth2Member.getAccessToken());
        String accessToken = oAuth2Member.getAccessToken();
        String refreshToken = oAuth2Member.getRefreshToken();

//        response.sendRedirect("https://food-saver.vercel.app/auth/oauth-response/" + accessToken);
        response.sendRedirect("http://localhost:3000/auth/oauth-response/" + accessToken);
    }

}
