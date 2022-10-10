package com.example.libertyformapiserver.utils.kakao;

import lombok.Getter;

@Getter
public class KakaoToken {
    String token_type;

    String access_token;
    String expires_in;

    String refresh_token;
    String refresh_token_expires_in;

    String scope;
}
