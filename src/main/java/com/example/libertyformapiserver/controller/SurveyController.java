package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.config.response.BaseResponseStatus;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.dto.survey.create.PostCreateSurveyReq;
import com.example.libertyformapiserver.dto.survey.create.PostCreateSurveyRes;
import com.example.libertyformapiserver.dto.survey.get.GetListSurveyRes;
import com.example.libertyformapiserver.dto.survey.get.GetSurveyInfoRes;
import com.example.libertyformapiserver.jwt.NoIntercept;
import com.example.libertyformapiserver.service.ObjectStorageService;
import com.example.libertyformapiserver.service.SurveyService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/survey")
@RequiredArgsConstructor
public class SurveyController {
    private final SurveyService surveyService;
    private final ObjectStorageService objectStorageService;

    // swagger에서 multipartFile 테스트 불가, 해당 테스트 작업 시 postman으로 수행
    @ApiOperation(
            value = "설문지 생성",
            notes = "survey - 설문 내용, questions - 주관식, 감정, 선형대수 문항," +
                    " choiceQuestions - 객관식 문항"
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2010, message = "존재하지 않는 유저입니다."),
            @ApiResponse(code = 2011, message = "질문 유형 번호를 다시한번 확인해주시길 바랍니다."),
            @ApiResponse(code = 2012, message = "질문 번호 순서가 올바르지 않습니다. 질문 번호를 다시한번 확인해주시기 바랍니다."),
            @ApiResponse(code = 4001, message = "존재하지 않는 질문 유형입니다."),
            @ApiResponse(code = 4002, message = "파일을 업로드 하는 도중 오류가 발생했습니다.")}
    )
    @PostMapping(value = "/create") // questionImgFiles은 설문 문항 번호로 구분이 됨 ex) 0.jpg, 1.png
    public BaseResponse<PostCreateSurveyRes> createSurvey(@RequestPart PostCreateSurveyReq surveyReqDto, HttpServletRequest request
            , @RequestParam(value = "thumbnailImg", required = false)MultipartFile thumbnailImgFile, @RequestParam(value = "questionImgs", required = false)List<MultipartFile> questionImgFiles){
        return new BaseResponse<>(surveyService.createSurvey(surveyReqDto, JwtInfo.getMemberId(request), thumbnailImgFile, questionImgFiles));
    }

    @ApiOperation(
            value = "설문지 모두 불러오기",
            notes = "해당 유저에 대한 설문을 모두 불러옵니다. (대시보드 용)"
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다.")
    })
    @GetMapping
    public BaseResponse<GetListSurveyRes> getAllUserSurvey(HttpServletRequest request){
        return new BaseResponse<>(surveyService.getAllUserSurvey(JwtInfo.getMemberId(request)));
    }

    @ApiOperation(
            value = "설문지 정보 가져오기",
            notes = "해당 설문에 대한 정보들을 가져옵니다"
    )
    @ApiResponses({
            @ApiResponse(code = 1000, message = "요청에 성공하였습니다."),
            @ApiResponse(code = 2013, message = "존재하지 않는 설문입니다."),
            @ApiResponse(code = 2014, message = "해당 사용자의 설문이 아닙니다.")
    })
    @GetMapping("{surveyId}")
    public BaseResponse<GetSurveyInfoRes> getSurveyInfo(HttpServletRequest request, @PathVariable("surveyId") long surveyId){
        return new BaseResponse<>(surveyService.getSurveyInfo(surveyId, JwtInfo.getMemberId(request)));
    }

    // 이미지 업로드 테스트
    @NoIntercept
    @PostMapping("/upload")
    public BaseResponse<String> uploadImgFile(@RequestParam("image")MultipartFile multipartFile){
        objectStorageService.uploadTest(multipartFile);
        return new BaseResponse<>(BaseResponseStatus.IMG_UPLOAD_SUCCESS);
    }
}
