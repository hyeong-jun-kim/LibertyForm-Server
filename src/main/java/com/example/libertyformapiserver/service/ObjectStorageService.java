package com.example.libertyformapiserver.service;

import com.example.libertyformapiserver.config.exception.BaseException;
import com.example.libertyformapiserver.config.response.BaseResponseStatus;
import com.example.libertyformapiserver.domain.Question;
import com.example.libertyformapiserver.domain.Survey;
import com.example.libertyformapiserver.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    private final String QUESTION_PATH = "/img/survey/question";

    private final RestTemplateService restTemplateService;

    private final QuestionRepository questionRepository;

    // 섬네일 이미지 업로드
    public void uploadThumbnailImg(Survey survey, MultipartFile thumbnailFile){
        if(thumbnailFile == null)
            return;

        StringBuilder sb = new StringBuilder(STORAGE_URL).append(THUMBNAIL_PATH);
        String storageURL = sb.toString();

        String fileURL = uploadFile(storageURL, thumbnailFile);

        survey.changeThumbnailImg(fileURL);
    }

    // 설문 문항 이미지 업로드
    public void uploadQuestionImgs(List<Question> questionList, List<MultipartFile> questionFileImgs){
        if(questionFileImgs == null)
            return;

        StringBuilder sb = new StringBuilder(STORAGE_URL).append(QUESTION_PATH);
        String storageURL = sb.toString();

        List<String> fileNameList = uploadMultipartFile(storageURL, questionFileImgs);

        for(int i = 0; i < questionFileImgs.size(); i++){
            MultipartFile multipartFile = questionFileImgs.get(i);

            String[] fileNames = multipartFile.getOriginalFilename().split("[.]");

            // 질문 유형 이미지 링크 넣기, 사진 이름은 1.jpg, 2.jpg 처럼 question의 질문 번호로 구분이 됨
            int idx = Integer.parseInt(fileNames[0]) - 1;
            Question question = questionList.get(idx);

            question.changeQuestionImgUrl(fileNameList.get(i));
        }
        questionRepository.saveAll(questionList);
    }

    private String uploadFile(String storageURL, MultipartFile multipartFile){
        String fileURL = getFileURL(multipartFile, storageURL);

        HttpHeaders headers = getApiTokenHeader();

        HttpEntity<String> response;

        response = restTemplateService.uploadFile(fileURL, headers, multipartFile, String.class);

        if(response == null)
            throw new BaseException(BaseResponseStatus.FILE_UPLOAD_ERROR);

        return fileURL;
    }
    @Async
    public List<String> uploadMultipartFile(String storageURL, List<MultipartFile> multipartFilesList){
        List<String> fileNameList = new ArrayList<>();

        HttpHeaders headers = getApiTokenHeader();

        for(MultipartFile multipartFile: multipartFilesList){
            String fileURL = getFileURL(multipartFile, storageURL);
            fileNameList.add(fileURL);

            HttpEntity<String> response;
            response = restTemplateService.uploadFile(fileURL, headers, multipartFile, String.class);

            if(response == null)
                throw new BaseException(BaseResponseStatus.FILE_UPLOAD_ERROR);
        }

        return fileNameList;
    }

    //
    public String getFileURL(MultipartFile multipartFile, String storageURL){
        StringBuilder sb = new StringBuilder(storageURL);
        String fileName = UUID.randomUUID() + "-" + multipartFile.getOriginalFilename();
        String fileURL = sb.append("/" + fileName).toString();
        return fileURL;
    }

    // API 인증 토큰 발급받기
    private HttpHeaders getApiTokenHeader(){
        JSONObject bodyObject = getApiTokenBodyObject();

        HttpEntity<String> response = restTemplateService.post(API_TOKEN_URL, HttpHeaders.EMPTY, bodyObject, String.class);
        HttpHeaders responseHeaders = response.getHeaders();

        String api_token = responseHeaders.getFirst("X-Subject-Token");

        HttpHeaders apiTokenHeader = new HttpHeaders();
        apiTokenHeader.add("X-Auth-Token", api_token);
        return apiTokenHeader;
    }

    // API 토큰 Body 값
    private JSONObject getApiTokenBodyObject(){
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


    // 업로드 테스트
    public void uploadTest(MultipartFile thumbnailFile){
        if(thumbnailFile == null)
            return;

        StringBuilder sb = new StringBuilder(STORAGE_URL).append(THUMBNAIL_PATH);
        String url = sb.toString();

        uploadFile(url, thumbnailFile);
    }
}
