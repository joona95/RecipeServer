package com.recipe.app.src.user.models;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class MypageMyRecipeList {
    private final Integer myRecipeIdx;
    private final String myRecipeTitle;
    private final String myRecipeThumbnail;
    private final String myRecipeDate;
}
