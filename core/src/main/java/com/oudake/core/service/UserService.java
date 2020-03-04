package com.oudake.core.service;

import com.oudake.core.model.TblUserInfo;

/**
 * @author wangyi
 */
public interface UserService {
    TblUserInfo findInfoByUserId(Integer userId);
}
