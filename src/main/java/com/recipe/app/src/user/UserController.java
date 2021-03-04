package com.recipe.app.src.user;

import com.recipe.app.config.BaseException;
import com.recipe.app.config.BaseResponse;
import com.recipe.app.src.user.models.*;
import com.recipe.app.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static com.recipe.app.config.BaseResponseStatus.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.*;



@RestController
@RequestMapping("/users")
public class UserController {
    private final UserProvider userProvider;
    private final UserService userService;
    private final JwtService jwtService;

    @Autowired
    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;
        this.userService = userService;
        this.jwtService = jwtService;
    }


    /**
     * 자동로그인 API
     * [POST] /users/auto-login
     */
    @ResponseBody
    @PostMapping("/auto-login")
    public BaseResponse<Void> postAutoLogin() {

        try {
            userProvider.autoLogin();
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 네이버 로그인 API
     * [POST] /users/naver-login
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("/naver-login")
    public BaseResponse<PostUserRes> postNaverLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("NAVER-ACCESS-TOKEN");
        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_TOKEN);
        }

        try {
            PostUserRes postUserRes = userService.naverLogin(accessToken);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 카카오 로그인 API
     * [POST] /users/kakao-login
     * @return BaseResponse<PostUserRes>
     */
    @ResponseBody
    @PostMapping("/kakao-login")
    public BaseResponse<PostUserRes> postKakaoLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("KAKAO-ACCESS-TOKEN");
        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_TOKEN);
        }

        try {
            PostUserRes postUserRes = userService.kakaoLogin(accessToken);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }
    /**
     * 구글 로그인 API
     * [POST] /users/google-login
     * @RequestBody parameters (accesstoken)
     */
    @ResponseBody
    @PostMapping("/google-login")
    public BaseResponse<PostUserRes> postGoogleLogin() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String accessToken = request.getHeader("GOOGLE-ACCESS-TOKEN");
        // 1. Body Parameter Validation
        if (accessToken == null || accessToken.length() == 0) {
            return new BaseResponse<>(EMPTY_TOKEN);
        }
        try {
            PostUserRes postUserRes = userService.googleLogin(accessToken);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


    /**
     * 회원 정보 조회 API
     * [GET] /users/:userIdx
     * @return BaseResponse<GetUserRes>
     */
    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<GetUserRes> getUser(@PathVariable Integer userIdx) {
        if(userIdx==null || userIdx<=0){
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }

        try {
            GetUserRes getUserRes = userProvider.retrieveUser(userIdx);
            return new BaseResponse<>(getUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 정보 수정 API
     * [PATCH] /users/:userIdx
     * @return BaseResponse<PatchUserRes>
     */
    @ResponseBody
    @PatchMapping("/{userIdx}")
    public BaseResponse<PatchUserRes> patchUser(@PathVariable Integer userIdx, @RequestBody PatchUserReq parameters) {
        if(userIdx==null || userIdx<=0){
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }

        try {
            PatchUserRes patchUserRes = userService.updateUser(userIdx, parameters);
            return new BaseResponse<>(patchUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 회원 탈퇴 API
     * [DELETE] /users/:userIdx
     */
    @ResponseBody
    @DeleteMapping("/{userIdx}")
    public BaseResponse<Void> deleteUser(@PathVariable Integer userIdx) {
        if(userIdx==null || userIdx<=0){
            return new BaseResponse<>(USERS_EMPTY_USER_ID);
        }

        try {
            userService.deleteUser(userIdx);
            return new BaseResponse<>(SUCCESS);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }


}
