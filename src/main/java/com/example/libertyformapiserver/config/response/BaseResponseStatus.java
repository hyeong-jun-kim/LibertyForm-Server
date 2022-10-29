package com.example.libertyformapiserver.config.response;

import lombok.Getter;

/**
 * 상태 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */

    SUCCESS(true, 1000, "요청에 성공하였습니다."),

    /**
     * 2000 : Request 오류
     */
    EMPTY_JWT(false, 2001, "header에 JWT가 없습니다."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다. 재로그인 바랍니다."),
    EXPIRED_JWT(false, 2003,"만료기간이 지난 JWT입니다. 재로그인 바랍니다."),
    NOT_MATCH_PASSWORD(false, 2004, "비밀번호 확인란이 일치하지 않습니다."),
    ALREADY_EXIST_EMAIL(false, 2005, "중복된 이메일 주소입니다."),
    NOT_EXIST_EMAIL(false, 2006, "존재하지 않는 이메일 입니다."),
    INVALID_PASSWORD(false, 2007, "비밀번호가 일치하지 않습니다."),
    INACTIVE_STATUS(false, 2008, "삭제된 데이터입니다."),
    KAKAO_TOKEN_ERROR(false, 2009, "카카오 토큰을 받아오는 수행 작업 중에 오류가 발생했습니다."),
    KAKAO_LOGIN_ERROR(false, 2009, "카카오 로그인을 시도하는 중에 오류가 발생했습니다."),
    INVALID_MEMBER(false, 2010, "존재하지 않는 유저입니다."),
    /**
     * 3000 : Response 오류
     */
    VALIDATED_ERROR(false, 3000, "VALIDATED_ERROR"), // @Valid 예외 처리

    /**
     * 4000 : Database, Server 오류
     */
    INTERNAL_SERVER_ERROR(false, 4000, "서버 오류입니다"),
    NOT_VALID_QUESTION_TYPE(false, 4001, "존재하지 않는 질문 유형입니다.");


    private final boolean isSuccess;
    private final int code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, int code, String message){
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
