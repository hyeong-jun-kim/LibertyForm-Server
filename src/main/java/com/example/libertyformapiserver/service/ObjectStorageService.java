package com.example.libertyformapiserver.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ObjectStorageService {
    @Value("${cloud.kakao.api_secret_token}")
    private String apiToken;
    private final String endPoint = "https://objectstorage.kr-central-1.kakaoi.io/v1/586d691a32c5421b859e89fd7a7f8dcd/libertyform";
    private final String thumbnail_path = "/img/survey/thumbnail";

    private final RestTemplateService restTemplateService;

    public String uploadThumbnailImg(MultipartFile multipartFile){
        StringBuilder sb = new StringBuilder(endPoint);
        sb.append(thumbnail_path);

        String response = uploadFile(sb.toString(), multipartFile);
        return response;
    }

    private String uploadFile(String url, MultipartFile multipartFile){
        HttpHeaders token_header = getTokenHttpHeaders();
        String response = (String) restTemplateService.uploadFile(url, token_header, multipartFile, String.class).getBody();
        return response;
    }

    private HttpHeaders getTokenHttpHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-Auth-Token", apiToken);
        return headers;
    }
}
