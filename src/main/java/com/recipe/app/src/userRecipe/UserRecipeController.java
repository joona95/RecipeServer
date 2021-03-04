package com.recipe.app.src.userRecipe;

import com.recipe.app.config.BaseException;
import com.recipe.app.config.BaseResponse;
import com.recipe.app.src.userRecipe.models.*;
import com.recipe.app.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.recipe.app.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/my-recipes")
public class UserRecipeController {
    private final UserRecipeProvider userRecipeProvider;
    private final UserRecipeService userRecipeService;
    private final JwtService jwtService;

    @Autowired
    public UserRecipeController(UserRecipeProvider userRecipeProvider, UserRecipeService userRecipeService, JwtService jwtService) {
        this.userRecipeProvider = userRecipeProvider;
        this.userRecipeService = userRecipeService;
        this.jwtService = jwtService;
    }

    /**
     * 나만의 레시피 전체조회 API
     * [GET] /my-recipes
     * @return BaseResponse<List<GetMyRecipesRes>>
     */
    @GetMapping("")
    public BaseResponse<List<GetMyRecipesRes>> getMyRecipes() {

        try {
            Integer userIdx = jwtService.getUserId();
            List<GetMyRecipesRes> GetMyRecipesResList = userRecipeProvider.retrieveMyRecipesList(userIdx);

            return new BaseResponse<>(GetMyRecipesResList);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 나만의 레시피 상세조회 API
     * [GET] /my-recipes/:myRecipeIdx
     * @return BaseResponse<GetMyRecipeRes>
     */
    @GetMapping("/{myRecipeIdx}")
    public BaseResponse<GetMyRecipeRes> getMyRecipe(@PathVariable Integer myRecipeIdx) {
        try {
            Integer userIdx = jwtService.getUserId();
            GetMyRecipeRes getMyRecipeRes = userRecipeProvider.retrieveMyRecipe(userIdx,myRecipeIdx);
            return new BaseResponse<>(getMyRecipeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * 나만의 레시피 삭제 API
     * [DELETE] /my-recipes/:myRecipeIdx
     * @return BaseResponse<GetMyRecipeRes>
     */
//    @DeleteMapping("/{myRecipeIdx}")
//    public BaseResponse<Void> deleteMyRecipe(@PathVariable Integer myRecipeIdx) {
//
////        try {
////            Integer userIdx = jwtService.getUserId();
////            if (myRecipeIdx == null || myRecipeIdx <= 0) {
////                return new BaseResponse<>(EMPTY_USERRECIPEIDX);
////            }
////
////            try {
////                userRecipeService.deleteUserRecipe(userIdx,myRecipeIdx);
////                return new BaseResponse<>(SUCCESS);
////            } catch (BaseException exception) {
////                return new BaseResponse<>(exception.getStatus());
////            }
////
////        } catch (BaseException exception) {
////            return new BaseResponse<>(exception.getStatus());
////        }
//
//        //            Integer userIdx = jwtService.getUserId();
//        Integer userIdx =1;
//        if (myRecipeIdx == null || myRecipeIdx <= 0) {
//            return new BaseResponse<>(EMPTY_USERRECIPEIDX);
//        }
//
//        try {
//            userRecipeService.deleteUserRecipe(userIdx,myRecipeIdx);
//            return new BaseResponse<>(SUCCESS);
//        } catch (BaseException exception) {
//            return new BaseResponse<>(exception.getStatus());
//        }
//
//    }
}