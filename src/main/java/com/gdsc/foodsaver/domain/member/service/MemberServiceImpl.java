package com.gdsc.foodsaver.domain.member.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.foodsaver.domain.member.entity.FavoriteRecipe;
import com.gdsc.foodsaver.domain.member.entity.Member;
import com.gdsc.foodsaver.domain.member.repository.MemberRepository;
import com.gdsc.foodsaver.domain.recipe.entity.Recipe;
import com.gdsc.foodsaver.global.auth.dto.GoogleAccessTokenInfoResponseDto;
import com.gdsc.foodsaver.global.auth.jwt.TokenParser;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberServiceImpl implements MemberService{
    private final MemberRepository memberRepository;
    private final TokenParser tokenParser;

    public List<Member> findAll() {
        return memberRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Member findMemberByOauthId(String oauthId) {
        return memberRepository.findByOauthId(oauthId).orElse(null);
    }

    public String retrieveName(HttpServletRequest request) {

        String hasPrefixAccessToken = tokenParser.parseBearerToken(request);
        log.info("hasPrefixtoekdn : " + hasPrefixAccessToken);

        String name = null;
        if (hasPrefixAccessToken.startsWith("google_")) {
            String accessToken = hasPrefixAccessToken.substring(7);
            name = "google_" + tokenPrefixGoogle(accessToken);
        }
        else
            return null;

        return name;
    }

    public String permitAllAccess(HttpServletRequest request) {

        String authorization = request.getHeader("Authorization");
        if(!StringUtils.hasText(authorization)) return null;

        return retrieveName(request);
    }

    private String tokenPrefixGoogle(String accessToken) {
        RestTemplate rt = new RestTemplate();

        // HttpHeaders 객체 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        // HttpHeader와 HttpBody를 하나의 객체로 담기
        HttpEntity<MultiValueMap<String, String>> kakaoTokenReq =
                new HttpEntity<>(null,headers);

        // Http 요청 - POST 방식, Response 변수의 응답
        ResponseEntity<String> resp = rt.exchange(
                "https://oauth2.googleapis.com/tokeninfo",
                HttpMethod.GET,
                kakaoTokenReq,
                String.class
        );

        ObjectMapper objectMapper = new ObjectMapper();
        GoogleAccessTokenInfoResponseDto accessTokenInfo = null;

        try {
            accessTokenInfo = objectMapper.readValue(resp.getBody(), GoogleAccessTokenInfoResponseDto.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        log.info(String.valueOf(accessTokenInfo));
        log.info("Member Id : " + accessTokenInfo.getSub());

        return accessTokenInfo.getSub();
    }
}
