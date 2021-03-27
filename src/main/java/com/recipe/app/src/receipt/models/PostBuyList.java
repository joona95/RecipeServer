package com.recipe.app.src.receipt.models;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Getter
public class PostBuyList {
    private String buyName;
    private Integer buyCnt;
    private Integer buyPrice;
}
