package com.fox.service;

import com.fox.domain.ResponseResult;
import com.fox.domain.User;

public interface SystemLoginService {
    //登录
    ResponseResult login(User user);

    //退出登录
    ResponseResult logout();
}
