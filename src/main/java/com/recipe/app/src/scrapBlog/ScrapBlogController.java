package com.recipe.app.src.scrapBlog;

import com.recipe.app.config.BaseException;
import com.recipe.app.config.BaseResponse;
import com.recipe.app.src.scrapBlog.models.*;
import com.recipe.app.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.recipe.app.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/scraps/blog")
public class ScrapBlogController {
    private final ScrapBlogProvider scrapBlogProvider;
    private final ScrapBlogService scrapBlogService;
    private final JwtService jwtService;

    @Autowired
    public ScrapBlogController(ScrapBlogProvider scrapBlogProvider, ScrapBlogService scrapBlogService , JwtService jwtService) {
        this.scrapBlogProvider = scrapBlogProvider;
        this.scrapBlogService = scrapBlogService;
        this.jwtService = jwtService;
    }

    /**
     * 유튜브 스크랩하기 API
     * [POST] /scraps/blog
     * @return BaseResponse<Void>
     * @RequestBody parameters
     */
/*
    @PostMapping("")
    public BaseResponse<Void> postScrapBlog(@RequestBody PostScrapBlogReq parameters) {

        try {
            Integer userIdx = jwtService.getUserId();

            if (parameters.getTitle() != null && parameters.getTitle() ==null) {
                return new BaseResponse<>(EMPTY_TITLE);
            }
            if (parameters.getThumbnail() == null && parameters.getThumbnail() !=null) {
                return new BaseResponse<>(EMPTY_THUMBNAIL);
            }
            if (parameters.getYoutubeUrl() != null && parameters.getYoutubeUrl() ==null) {
                return new BaseResponse<>(EMPTY_YOUTUBEURL);
            }
            if (parameters.getPostDate() != null && parameters.getPostDate() ==null) {
                return new BaseResponse<>(EMPTY_POST_DATE);
            }
            if (parameters.getChannelName() != null && parameters.getChannelName() ==null) {
                return new BaseResponse<>(EMPTY_CHANNEL_NAME);
            }
            if (parameters.getYoutubeIdx() == null && parameters.getYoutubeIdx() !=null) {
                return new BaseResponse<>(EMPTY_YOUTUBEIDX);
            }

            // 이미 발급 받은건지
            ScrapYoutube scrapYoutube = null;
            scrapYoutube = scrapYoutubeProvider.retrieveScrapYoutube(parameters.getYoutubeIdx(), userIdx);


            PostScrapYoutubeRes postScrapYoutubeRes;
            if (scrapYoutube != null) {
                postScrapYoutubeRes = scrapYoutubeService.deleteScrapYoutube(parameters.getYoutubeIdx(),userIdx);
                return new BaseResponse<>(postScrapYoutubeRes);
            }
            else{
                postScrapYoutubeRes = scrapYoutubeService.createScrapYoutube(parameters,userIdx);
                return new BaseResponse<>(postScrapYoutubeRes);
            }

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }
*/


    /**
     * 유튜브 스크랩 조회 API
     * [GET] /scraps/youtube
     * @return BaseResponse<List<GetScrapYoutubesRes>>
     * @PageableDefault pageable
     */
    /*
    @GetMapping("")
    public BaseResponse<GetScrapYoutubesRes> getScrapYoutubes(@RequestParam(value = "sort") @Nullable Integer sort) {

        GetScrapYoutubesRes getScrapYoutubesRes = null;
        try {
            Integer userIdx = jwtService.getUserId();

            getScrapYoutubesRes = scrapYoutubeProvider.retrieveScrapYoutubes(userIdx,sort);

            return new BaseResponse<>(getScrapYoutubesRes);

        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }*/
}
