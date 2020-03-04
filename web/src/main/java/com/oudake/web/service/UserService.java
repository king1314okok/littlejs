package com.oudake.web.service;

import com.oudake.common.ResultEntity;
import com.oudake.web.model.TblUserInfo;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangyi
 */
public interface UserService {
    TblUserInfo findInfoByUserId(Integer userId);

    ResultEntity addUserInfo(TblUserInfo userInfo);

    ResultEntity editUserInfo(TblUserInfo userInfo);

    ResultEntity setLoginInfo(Integer userId, HttpServletRequest request);
}
