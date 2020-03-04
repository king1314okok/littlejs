package com.oudake.core.service.impl;

import com.oudake.core.dao.TblUserInfoMapper;
import com.oudake.core.model.TblUserInfo;
import com.oudake.core.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangyi
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TblUserInfoMapper userInfoMapper;

    @Override
    public TblUserInfo findInfoByUserId(Integer userId) {
        return userInfoMapper.findByUserId(userId);
    }
}
