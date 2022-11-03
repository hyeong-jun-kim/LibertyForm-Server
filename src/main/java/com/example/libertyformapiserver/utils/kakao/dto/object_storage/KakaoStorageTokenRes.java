package com.example.libertyformapiserver.utils.kakao.dto.object_storage;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoStorageTokenRes {
    private Map<String, String> headers;
}
