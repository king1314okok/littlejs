package com.oudake.console.service;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblUser;
import com.oudake.console.model.TblUserInfo;

import java.util.List;

/**
 * @author wangyi
 */
public interface UserService {
    Layui findUserByPage(TblUser user, Page page);

    TblUser findUserByUserId(Integer userId);

    TblUserInfo findInfoByUserId(Integer userId);

    ResultEntity freezeUser(Integer userId);

    ResultEntity recoverUser(Integer userId);

    List<TblUserInfo> getActiveUser();

    Integer getMonthActiveUserAmount();

    Integer getLastMonthActiveUserAmount();
}
