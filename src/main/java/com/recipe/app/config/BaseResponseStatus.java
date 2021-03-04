package com.recipe.app.config;

import lombok.Getter;

/**
 * 에러 코드 관리
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
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),
    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    EMPTY_TOKEN(false, 2003, "ACCESS TOKEN 값을 입력해주세요."),
    EMPTY_USERRECIPEIDX(false, 2004, "나만의 레시피 인덱스값을 입력해주세요."),
    // users
    USERS_EMPTY_USER_ID(false, 2010, "유저 아이디 값을 확인해주세요."),

    // [POST] /users
    POST_USERS_EMPTY_EMAIL(false, 2015, "이메일을 입력해주세요."),
    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    POST_USERS_EMPTY_PASSWORD(false, 2017, "비밀번호를 입력해주세요."),
    POST_USERS_EMPTY_CONFIRM_PASSWORD(false, 2018, "비밀번호 확인을 입력해주세요."),
    POST_USERS_WRONG_PASSWORD(false, 2019, "비밀번호를 다시 입력해주세요."),
    POST_USERS_DO_NOT_MATCH_PASSWORD(false, 2020, "비밀번호와 비밀번호확인 값이 일치하지 않습니다."),
    POST_USERS_EMPTY_NICKNAME(false, 2021, "닉네임을 입력해주세요."),

    // [PATCH] /users/:userId
    PATCH_USERS_DO_NOT_MATCH_PASSWORD(false, 2022, "비밀번호와 비밀번호확인 값이 일치하지 않습니다."),

    // [POST] /users/login
    POST_USERS_LOGIN_EMPTY_EMAIL(false, 2023, "이메일을 입력해주세요."),
    POST_USERS_LOGIN_INVALID_EMAIL(false, 2024, "이메일 형식을 확인해주세요."),
    POST_USERS_LOGIN_EMPTY_PASSWORD(false, 2025, "비밀번호를 입력해주세요."),

    // [PATCH] /users/:userId/status
    PATCH_USERS_STATUS_INVALID_USER_STATUS(false, 2026, "상태코드를 확인해주세요."),

    // boards
    BOARDS_EMPTY_BOARD_ID(false, 2040, "게시판 아이디 값을 확인해주세요."),

    // [POST] /boards
    POST_BOARDS_EMPTY_TITLE(false, 2041, "게시판 제목을 입력해주세요."),
    POST_BOARDS_EMPTY_CONTENTS(false, 2042, "내용을 입력해주세요."),

    // [PATCH] /boards/:boardId
    PATCH_BOARDS_EMPTY_TITLE(false, 2043, "게시판 제목을 입력해주세요."),
    PATCH_BOARDS_EMPTY_CONTENTS(false, 2044, "내용을 입력해주세요."),

    // [PATCH] /boards/:boardId/status
    PATCH_BOARDS_STATUS_INVALID_BOARD_STATUS(false, 2045, "상태코드를 확인해주세요."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    // users
    NOT_FOUND_USER(false, 3010, "존재하지 않는 회원입니다."),
    FORBIDDEN_USER(false, 3011, "해당 회원에 접근할 수 없습니다."),

    // [POST] /users
    DUPLICATED_USER(false, 3013, "이미 존재하는 회원입니다."),

    // [POST] /users/naver-login
    FAILED_TO_NAVER_LOGIN(false, 3015, "네이버 로그인에 실패하였습니다."),

    // [POST] /users/kakao-login
    FAILED_TO_KAKAO_LOGIN(false, 3016, "카카오 로그인에 실패하였습니다."),

    FAILED_TO_GET_USER_RECIPE(false, 3017, "유저레시피 상세 조회에 실패하였습니다."),
    FAILED_TO_GET_USER_RECIPE_PHOTO(false, 3018, "유저레시피 사진 조회에 실패하였습니다."),
    FAILED_TO_GET_USER_RECIPES(false, 3019, "유저레시피 전체 조회에 실패하였습니다."),

    FAILED_TO_GET_USER(false, 3020, "유저 상세 정보 조회에 실패하였습니다."),
    FAILED_TO_PATCH_USER(false, 3021, "유저 정보 수정에 실패하였습니다."),


    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),

    PASSWORD_ENCRYPTION_ERROR(false, 4011, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4012, "비밀번호 복호화에 실패하였습니다."),

    //소셜 사용자 정보 접근
    WRONG_URL(false, 4021, "API URL이 잘못되었습니다."),
    FAILED_TO_CONNECT(false, 4022, "연결이 실패했습니다."),
    FAILED_TO_READ_RESPONSE(false, 4023, "API 응답을 읽는데 실패했습니다."),
    FAILED_TO_PARSE(false, 4024, "JSON 파싱에 실패했습니다."),
    FORBIDDEN_ACCESS(false, 4025, "접근 권한이 없습니다."),
    ;


    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
