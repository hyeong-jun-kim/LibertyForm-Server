package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.config.response.BaseResponseStatus;
import com.example.libertyformapiserver.utils.kakao.dto.object_storage.KakaoStorageTokenBody;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ObjectStorageService {
    @Value("${cloud.kakao.access_key}")
    private String access_key;
    @Value("${cloud.kakao.secret_key}")
    private String secret_key;

    private final String API_TOKEN_URL = "https://iam.kakaoi.io/identity/v3/auth/tokens";
    private final String STORAGE_URL = "https://objectstorage.kr-central-1.kakaoi.io/v1/586d691a32c5421b859e89fd7a7f8dcd/libertyform";
    private final String THUMBNAIL_PATH = "/img/survey/thumbnail";

    private final RestTemplateService restTemplateService;

    // 섬네일 이미지 업로드
    public String uploadThumbnailImg(MultipartFile multipartFile){
        StringBuilder sb = new StringBuilder(STORAGE_URL);
        sb.append(THUMBNAIL_PATH);

        String response = uploadFile(sb.toString(), multipartFile);
        return response;
    }

    private String uploadFile(String url, MultipartFile multipartFile){
        HttpEntity<String> response;

        HttpHeaders headers = getApiTokenHeader();
        response = restTemplateService.uploadFile(url, headers, multipartFile, String.class);

        if(response == null)
            throw new BaseException(BaseResponseStatus.FILE_UPLOAD_ERROR);

        return response.toString();
    }

    // API 인증 토큰 발급받기
    private HttpHeaders getApiTokenHeader(){
        JSONObject bodyObject = getBodyObject();

        HttpEntity<String> response = restTemplateService.post(API_TOKEN_URL, HttpHeaders.EMPTY, bodyObject, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        String api_token = responseHeaders.getFirst("X-Subject-Token");

        HttpHeaders apiTokenHeader = new HttpHeaders();
        apiTokenHeader.add("X-Auth-Token", api_token);
        return apiTokenHeader;
    }

    private JSONObject getBodyObject(){
        JSONObject bodyObject = new JSONObject();
        JSONObject authObject = new JSONObject();
        JSONObject identityObject = new JSONObject();

        List<String> methodList = new ArrayList<>();
        methodList.add("application_credential");

        identityObject.put("methods", methodList);

        JSONObject credentialObject = new JSONObject();
        credentialObject.put("id", access_key);
        credentialObject.put("secret", secret_key);

        identityObject.put("application_credential", credentialObject);

        authObject.put("identity", identityObject);

        bodyObject.put("auth", authObject);
        return bodyObject;
    }

}
