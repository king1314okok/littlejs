package com.oudake.web.service;

import com.oudake.common.ResultEntity;
import com.oudake.web.model.TblUser;

/**
 * @author wangyi
 */
public interface LoginService {

    TblUser findByUsername(String username);

    boolean isUserExist(String username);

    ResultEntity login(String username, String password);
}
