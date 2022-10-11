package com.example.libertyformapiserver.utils.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoTokenDTO {
    String access_token;

    String refresh_token;
}
