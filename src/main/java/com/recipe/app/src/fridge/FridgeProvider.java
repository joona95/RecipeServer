package com.recipe.app.src.fridge;


import com.recipe.app.config.BaseException;
import com.recipe.app.src.fridge.models.Fridge;
import com.recipe.app.src.fridge.models.FridgeList;
import com.recipe.app.src.fridge.models.GetFridgesRes;
import com.recipe.app.src.ingredient.models.Ingredient;
import com.recipe.app.src.ingredient.models.IngredientList;
import com.recipe.app.src.ingredientCategory.IngredientCategoryProvider;
import com.recipe.app.src.ingredientCategory.IngredientCategoryRepository;
import com.recipe.app.src.ingredientCategory.models.IngredientCategory;
import com.recipe.app.src.user.UserProvider;
import com.recipe.app.src.user.models.User;
import com.recipe.app.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.recipe.app.config.BaseResponseStatus.*;

@Service
public class FridgeProvider {
    private final UserProvider userProvider;
    private final FridgeRepository fridgeRepository;
    private final IngredientCategoryProvider ingredientCategoryProvider;
    private final IngredientCategoryRepository ingredientCategoryRepository;
    private final JwtService jwtService;

    @Autowired
    public FridgeProvider(UserProvider userProvider, FridgeRepository fridgeRepository, IngredientCategoryProvider ingredientCategoryProvider, IngredientCategoryRepository ingredientCategoryRepository, JwtService jwtService) {
        this.userProvider = userProvider;
        this.fridgeRepository = fridgeRepository;
        this.ingredientCategoryProvider = ingredientCategoryProvider;
        this.ingredientCategoryRepository = ingredientCategoryRepository;
        this.jwtService = jwtService;
    }

    /**
     * 냉장고 조회 API
     * @param userIdx
     * @return List<GetFridgesRes>
     * @throws BaseException
     */
    public List<GetFridgesRes> retreiveFridges(int userIdx) throws BaseException {


        List<IngredientCategory> ingredientCategoryList;
        try {
            // 카테고리 리스트 뽑기
            ingredientCategoryList = ingredientCategoryRepository.findByStatus("ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_GET_INGREDIENT_CATEGORY);
        }



        return ingredientCategoryList.stream().map(ic -> {

            // 카테고리당 인덱스와 카테고리명 뽑기
            Integer ingredientCategoryIdx = ic.getIngredientCategoryIdx();
            String ingredientCategoryName = ic.getName();

            List<FridgeList> fridgeList = null;
            try {
                // 카테고리에 해당하는 재료 리스트 가져오기
                fridgeList = retreiveFridgeList(ingredientCategoryIdx,userIdx);
            } catch (BaseException e) {
                e.printStackTrace();
            }


            return new GetFridgesRes(ingredientCategoryIdx, ingredientCategoryName, fridgeList);


        }).collect(Collectors.toList());
//        User user = userProvider.retrieveUserByUserIdx(userIdx);
//        List<Fridge> fridgeList;
//        try {
//            fridgeList = fridgeRepository.findByUserAndStatus(user,"ACTIVE");
//        } catch (Exception ignored) {
//            throw new BaseException(FAILED_TO_RETREIVE_FRIDGE_BY_USER);
//        }
//
//        return fridgeList.stream().map(fridge -> {
//
//            String ingredientName = fridge.getIngredientName();
//            String ingredientIcon = fridge.getIngredientIcon();
//            Date tmpDate = fridge.getExpiredAt();
//
//            DateFormat sdFormat = new SimpleDateFormat("yy.MM.dd");
//            String expiredAt = sdFormat.format(tmpDate);
//
//            String storageMethod = fridge.getStorageMethod();
//            Integer count = fridge.getCount();
//
//            Integer freshness;
//
//            Date tempDate = new Date();
//            String nowDate = sdFormat.format(tempDate);
//
//            SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");
//
//            long diffDay = 0;
//            try{
//                Date startDate = sdf.parse(nowDate);
//                Date endDate = sdf.parse(expiredAt);
//                //두날짜 사이의 시간 차이(ms)를 하루 동안의 ms(24시*60분*60초*1000밀리초) 로 나눈다.
//                diffDay = (endDate.getTime() - startDate.getTime()) / (24*60*60*1000);
//            }catch(ParseException e){
//                e.printStackTrace();
//            }
//
//            if (diffDay<0){
//                freshness=444;
//            }
//            else if(diffDay<=3){
//                freshness=1;
//            }
//            else if( diffDay <=7){
//                freshness=2;
//            }
//            else{
//                freshness=3;
//            }
//
//
//
//            return new GetFridgesRes(ingredientName,ingredientIcon,expiredAt+"까지",storageMethod,count,freshness);
//
//        }).collect(Collectors.toList());
    }


    /**
     * 카테고리에 해당하는 냉장고 재료 리스트 추출
     *
     * @param ingredientCategoryIdx
     * @return List<FridgeList>
     * @throws BaseException
     */
    public List<FridgeList> retreiveFridgeList(Integer ingredientCategoryIdx,int userIdx) throws BaseException {
        User user = userProvider.retrieveUserByUserIdx(userIdx);
        IngredientCategory ingredientCategory = ingredientCategoryProvider.retrieveIngredientCategoryByIngredientCategoryIdx(ingredientCategoryIdx);

        List<Fridge> fridgeList;
        try {
            fridgeList = fridgeRepository.findByUserAndIngredientCategoryAndStatus(user,ingredientCategory,"ACTIVE");
        } catch (Exception ignored) {
            throw new BaseException(FAILED_TO_GET_INGREDIENT_LIST);
        }

        // 카테고리에 해당하는 재료 리스트 생성
        return fridgeList.stream().map(fl -> {
            String ingredientName = fl.getIngredientName();
            String ingredientIcon = fl.getIngredientIcon();

            Date tmpDate = fl.getExpiredAt();
            DateFormat sdFormat = new SimpleDateFormat("yy.MM.dd");
            String expiredAt = sdFormat.format(tmpDate);

            String storageMethod = fl.getStorageMethod();
            Integer count =fl.getCount();
            Integer freshness;

            Date tempDate = new Date();
            String nowDate = sdFormat.format(tempDate);
            SimpleDateFormat sdf = new SimpleDateFormat("yy.MM.dd");
            long diffDay = 0;
            try{
                Date startDate = sdf.parse(nowDate);
                Date endDate = sdf.parse(expiredAt);
                //두날짜 사이의 시간 차이(ms)를 하루 동안의 ms(24시*60분*60초*1000밀리초) 로 나눈다.
                diffDay = (endDate.getTime() - startDate.getTime()) / (24*60*60*1000);
            }catch(ParseException e){
                e.printStackTrace();
            }

            if (diffDay<0){
                freshness=444;
            }
            else if(diffDay<=3){
                freshness=1;
            }
            else if( diffDay <=7){
                freshness=2;
            }
            else{
                freshness=3;
            }

            return new FridgeList(ingredientName,ingredientIcon,expiredAt+"까지",storageMethod,count,freshness);
        }).collect(Collectors.toList());
    }


}
