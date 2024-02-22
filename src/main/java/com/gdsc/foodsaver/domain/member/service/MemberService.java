package com.gdsc.foodsaver.domain.member.service;

import com.gdsc.foodsaver.domain.member.entity.Member;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface MemberService {
    public List<Member> findAll();
    public Member findMemberByOauthId(String oauthId);
    public String retrieveName(HttpServletRequest request);
    String permitAllAccess(HttpServletRequest request);
}
