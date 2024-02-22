package com.gdsc.foodsaver.domain.member.service;

import com.gdsc.foodsaver.domain.member.entity.CustomOAuth2Member;
import com.gdsc.foodsaver.domain.member.entity.Member;
import com.gdsc.foodsaver.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class OAuth2MemberServiceImpl extends DefaultOAuth2UserService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    @Override
    public OAuth2User loadUser(OAuth2UserRequest request) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(request);

        String oauthClientName = request.getClientRegistration().getClientName();
        String accessToken = request.getAccessToken().getTokenValue();
        String refreshToken = null;

        log.info("accessToken : " + accessToken);

        Member member = null;
        String oauthId = null;
        String email = null;

        if(oauthClientName.equals("google")) {
            Map<String, Object> properties = (Map<String, Object>) oAuth2User.getAttributes().get("properties");

            oauthId = "google_" + oAuth2User.getAttributes().get("sub");
            email = oAuth2User.getAttributes().get("email").toString();

            member = new Member(oauthId, email, "google");
        }

        if(memberService.findMemberByOauthId(member.getOauthId()) == null) {
            memberRepository.save(member);
        }
//        else {
//            // update logic
//        }

        if (refreshToken == null) return new CustomOAuth2Member(oauthClientName + "_" + accessToken, null);
        else return new CustomOAuth2Member(oauthClientName + "_" + accessToken, oauthClientName + "_" + refreshToken);
    }

}
