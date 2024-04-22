package com.d205.foorrng.common.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    //아티클
    ARTICLE_NOT_EXIST(HttpStatus.BAD_REQUEST, "A-001", "존재하지 않은 게시글입니다. "),

    // 회원
    USER_NOT_EXIST(HttpStatus.BAD_REQUEST, "U-001", "존재하지 않는 회원입니다."),
    EMAIL_EXIST(HttpStatus.BAD_REQUEST, "U-002", "이미 가입된 회원입니다."),
    CATEGORY_NOT_EXIST(HttpStatus.BAD_REQUEST, "U-003", "존재하지 않는 항목입니다."),
    BUSINESSNUMBER_NOT_VALIDATE(HttpStatus.BAD_REQUEST, "U-004", "사업자번호 조회 실패"),
    LIKES_NOT_FOUND(HttpStatus.BAD_REQUEST, "U-005", "좋아요가 존재하지 않습니다"),

    // 음식
    FOODLIST_NOT_EXIST(HttpStatus.BAD_REQUEST, "F-001", "선호음식 리스트가 존재하지 않습니다."),

    // 토큰
    UNEXPECTED_TOKEN(HttpStatus.BAD_REQUEST, "T-001", "토큰이 만료되었습니다."),
    TOKEN_NOT_EXIST(HttpStatus.BAD_REQUEST, "T-002", "토큰이 존재하지 않습니다."),

    // 푸드트럭
    FOODTRUCK_NOT_EXIST(HttpStatus.BAD_REQUEST, "F-001", "존재하지 않는 푸드트럭입니다."),
    Foodtruck_Delete_ALREADY_EXIST(HttpStatus.BAD_REQUEST, "D-001", "이미 삭제 요청이 접수되었습니다"),

    // 마커
    MARK_NOT_EXIST(HttpStatus.BAD_REQUEST, "M-001", "해당 푸드트럭에 대한 위치정보가 없습니다."),
    MARK_OCCUPIED(HttpStatus.BAD_REQUEST, "M-002", "해당 푸드트럭에 대한 위치정보를 더 이상 생성할 수 없습니다."),

    // 운영정보
    OPERATION_NOT_EXIST(HttpStatus.BAD_REQUEST, "O-001", "운영정보가 없습니다."),
    DAY_OCCUPIED(HttpStatus.BAD_REQUEST, "O-002", "해당 요일은 이미 운영중입니다."),
    DAY_NOT_VALID(HttpStatus.BAD_REQUEST, "O-003", "유효하지 않은 요일 형태입니다."),


    // Validation
    NOT_VALID_REQUEST(HttpStatus.BAD_REQUEST, "I-001", "요청변수가 유효하지 않습니다."),

    // 메뉴
    MENU_NOT_FOUND(HttpStatus.BAD_REQUEST, "M-001", "존재하지 않는 메뉴입니다."),

    // 리뷰
    REVIEW_TODAY_EXIST(HttpStatus.BAD_REQUEST, "R-001", "이미 작성한 리뷰입니다"),

    // 축제
    FESTIVAL_NOT_EXIST(HttpStatus.BAD_REQUEST, "FV-001", "축제가 존재하지 않습니다."),

    // 지역
    BOUNDARY_NOT_EXIST(HttpStatus.BAD_REQUEST, "B-001", "지역경계 정보가 없습니다.")
    ;

    // 상태, 에러 코드, 메시지
    private HttpStatus httpStatus;
    private String errorCode;
    private String message;

    // 생성자
    ErrorCode(HttpStatus httpStatus, String errorCode, String message) {
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
        this.message = message;
    }
}
