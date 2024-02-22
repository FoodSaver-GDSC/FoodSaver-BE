package com.gdsc.foodsaver.domain.recipe.handler;

import com.gdsc.foodsaver.global.common.ResponseCode;
import com.gdsc.foodsaver.global.error.GeneralException;

public class RecipeHandler extends GeneralException {
    public RecipeHandler(ResponseCode errorCode) {
        super(errorCode);
    }
}
