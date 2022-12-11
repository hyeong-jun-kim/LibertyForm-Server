package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.config.response.BaseResponseStatus;
import com.example.libertyformapiserver.dto.flask.post.PostEmotionAnalysisDto;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.dto.response.post.PostResponseReq;
import com.example.libertyformapiserver.dto.response.post.PostResponseRes;
import com.example.libertyformapiserver.jwt.NoIntercept;
import com.example.libertyformapiserver.jwt.ResponseIntercept;
import com.example.libertyformapiserver.service.FlaskService;
import com.example.libertyformapiserver.service.ObjectStorageService;
import com.example.libertyformapiserver.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;

import static com.example.libertyformapiserver.config.response.BaseResponseStatus.EMOTION_ANALYSIS_SUCCESS;
import static com.example.libertyformapiserver.config.response.BaseResponseStatus.WORD_CLOUD_SUCCESS;

@Log4j2
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/response")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;
    private final FlaskService flaskService;

    private final ObjectStorageService objectStorageService;

    @ApiOperation(
            value = "피설문자 응답 생성",
            notes = "피설문자에 대한 응답을 생성하는 API 입니다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2013, message = "존재하지 않는 설문지입니다."),
            @ApiResponse(code = 2015, message = "존재하지 않는 질문입니다."),
            @ApiResponse(code = 2016, message = "존재하지 않는 선택지입니다.")
    })
    @PostMapping(value = "/create") // questionImgFiles은 설문 문항 번호로 구분이 됨 ex) 0.jpg, 1.png
    @ResponseIntercept
    public BaseResponse<PostResponseRes> createSurvey(@RequestBody @Validated PostResponseReq postResponseReq, HttpServletRequest request){
        PostResponseRes postResponseRes = responseService.createResponse(postResponseReq, JwtInfo.getMemberId(request));
        log.info("Create Response : {}", postResponseRes.getResponseId());

        return new BaseResponse<>(postResponseRes);
    }

    /**
     * Flask 연동 컨트롤러
     */

    // 감정분석 업데이트
    @ApiOperation(
            value = "감정 분석 업데이트 (Flask 연동 용)",
            notes = "Flask에서 감정 분석 업데이트를 위한 API 입니다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2022, message = "설문 응답 데이터가 존재하지 않습니다.")
    })
    @NoIntercept
    @PostMapping("/emotion")
    public BaseResponse<String> updateEmotionAnalysis(@RequestBody PostEmotionAnalysisDto postEmotionAnalysisDto){
        responseService.updateEmotionType(postEmotionAnalysisDto);

        return new BaseResponse<>(EMOTION_ANALYSIS_SUCCESS);
    }

    // 워드클라우드 이미지 업로드
    @ApiOperation(
            value = "워드 클라우드 이미지 업로드 (Flask 연동 용)",
            notes = "Flask에서 워드클라우드 이미지 업로드를 위한 API 입니다."
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2015, message = "존재하지 않는 질문입니다.")
    })
    @NoIntercept
    @PostMapping("/upload/{questionId}")
    public BaseResponse<String> uploadWordCloudImg(@PathVariable("questionId") long questionId, @RequestParam("image")MultipartFile multipartFile){
        objectStorageService.uploadWordCloudImg(multipartFile, questionId);

        return new BaseResponse<>(WORD_CLOUD_SUCCESS);
    }

    // 감정 분석 정보전송 테스트
    @ApiOperation(
            value = "감정 분석 response 전송 테스트 (Flask 연동 용)",
            notes = "Flask에서 감정 분석 테스트를 위한 API 입니다."
    )
    @NoIntercept
    @GetMapping("/test/emotion")
    public BaseResponse<String> emotionTest(){
        flaskService.sendTextResponse(37);

        return new BaseResponse<>(EMOTION_ANALYSIS_SUCCESS);
    }

    // 워드클라우드 정보전송 테스트
    @ApiOperation(
            value = "워드클라우드 response 전송 테스트 (Flask 연동 용)",
            notes = "Flask에서 워드클라우드 분석 테스트를 위한 API 입니다."
    )
    @NoIntercept
    @GetMapping("/test/wordcloud")
    public BaseResponse<String> wordCloudTest(){
        flaskService.sendTextResponse(37);

        return new BaseResponse<>(WORD_CLOUD_SUCCESS);
    }
}
