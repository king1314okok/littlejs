package com.oudake.console.service;

import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblSysUser;

/**
 * @author wangyi
 */
public interface LoginService {

    TblSysUser findByUsername(String username);

    boolean isUserExist(String username);

    ResultEntity login(String username, String password);

    ResultEntity findPwd(String username, String userEmail);
}
