package com.gdsc.foodsaver.domain.member.handler;


import com.gdsc.foodsaver.global.common.ResponseCode;
import com.gdsc.foodsaver.global.error.GeneralException;

public class MemberHandler extends GeneralException {
    public MemberHandler(ResponseCode errorCode) {

        super(errorCode);
    }
}
