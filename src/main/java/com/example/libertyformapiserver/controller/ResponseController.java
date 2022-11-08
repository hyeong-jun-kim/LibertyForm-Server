package com.example.libertyformapiserver.controller;

import com.example.libertyformapiserver.config.response.BaseResponse;
import com.example.libertyformapiserver.dto.jwt.JwtInfo;
import com.example.libertyformapiserver.dto.response.post.PostResponseReq;
import com.example.libertyformapiserver.dto.response.post.PostResponseRes;
import com.example.libertyformapiserver.jwt.ResponseIntercept;
import com.example.libertyformapiserver.service.ResponseService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Log4j2
@RestController()
@RequestMapping("/response")
@RequiredArgsConstructor
public class ResponseController {
    private final ResponseService responseService;

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
}
