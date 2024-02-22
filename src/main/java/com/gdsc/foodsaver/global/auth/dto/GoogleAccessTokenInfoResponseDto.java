package com.gdsc.foodsaver.global.auth.dto;

import lombok.Getter;

@Getter
public class GoogleAccessTokenInfoResponseDto {
    private String azp;
    private String aud;
    private String sub;
    private String scope;
    private String exp;
    private String expires_in;
    private String email;
    private String email_verified;
    private String access_type;
}
