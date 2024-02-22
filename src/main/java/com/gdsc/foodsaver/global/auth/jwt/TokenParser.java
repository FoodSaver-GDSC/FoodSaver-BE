package com.gdsc.foodsaver.global.auth.jwt;

import com.gdsc.foodsaver.domain.member.handler.MemberHandler;
import com.gdsc.foodsaver.global.common.ResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

@Component
public class TokenParser {
    public String parseBearerToken(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");

        boolean hasAuthorization = StringUtils.hasText(authorization);
        if(!hasAuthorization) throw new MemberHandler(ResponseCode.ACCESS_TOKEN_NOT_FOUND);

        boolean isBearer = authorization.startsWith("Bearer ");
        if(!isBearer) throw new MemberHandler(ResponseCode.BEARER_PREFIX_VALUE_EXCEPTION);

        String token = authorization.substring(7);
        return token;
    }
}
