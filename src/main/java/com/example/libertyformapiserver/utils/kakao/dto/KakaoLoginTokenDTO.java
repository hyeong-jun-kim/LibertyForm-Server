package com.example.libertyformapiserver.utils.kakao.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoLoginTokenDTO {
    String access_token;

    String refresh_token;
}
