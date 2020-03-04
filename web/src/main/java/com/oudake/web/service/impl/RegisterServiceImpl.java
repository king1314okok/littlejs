package com.oudake.web.service.impl;

import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.web.dao.TblUserInfoMapper;
import com.oudake.web.dao.TblUserMapper;
import com.oudake.web.model.TblUser;
import com.oudake.web.model.TblUserInfo;
import com.oudake.web.service.LoginService;
import com.oudake.web.service.RegisterService;
import com.oudake.web.service.SysCodeService;
import com.oudake.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

/**
 * @author wangyi
 */
@Service
public class RegisterServiceImpl implements RegisterService {

    private final static Logger LOGGER = LoggerFactory.getLogger(RegisterServiceImpl.class);

    @Autowired
    private TblUserMapper userMapper;
    @Autowired
    private TblUserInfoMapper userInfoMapper;
    @Autowired
    private LoginService loginService;
    @Autowired
    private SysCodeService sysCodeService;
    @Autowired
    private UserService userService;

    /**
     * 用户注册入库
     * @param user user
     * @return resultEntity
     */
    @Transactional
    @Override
    public ResultEntity register(TblUser user) {
        if (loginService.isUserExist(user.getUsername())) {
            return new ResultEntity(false, "此用户名已被使用");
        }

        String userType = sysCodeService.findSysCodeByTypeAndName(Constants.UserType.NAME,  Constants.UserType.NORMAL).getDisplay1();
        String userStatus = sysCodeService.findSysCodeByTypeAndName(Constants.UserStatus.NAME,  Constants.UserStatus.NORMAL).getDisplay1();
        user.setUserType(userType);
        user.setUserStatus(userStatus);

        int result = userMapper.insert(user);
        if (result > 0) {
            TblUserInfo userInfo = new TblUserInfo();
            // 查找之前入库的用户id
            userInfo.setUserId(userMapper.findUserByUsername(user.getUsername()).getUserId());
            // 用户信息创建
            ResultEntity response = userService.addUserInfo(userInfo);
            try {
                if (!response.isSuccess()) {
                    throw new RuntimeException();
                }
            } catch (RuntimeException e) {
                LOGGER.info(">>RegisterImpl: 用户 " + user.getUsername() + " 注册失败,进行回滚");
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return response;
            }
            LOGGER.info(">>RegisterImpl: 用户 " + user.getUsername() + " 注册成功");
            return new ResultEntity(true, "注册成功");
        } else {
            LOGGER.info(">>RegisterImpl: 用户 " + user.getUsername() + " 注册失败");
            return new ResultEntity(false, "注册失败");
        }
    }
}
