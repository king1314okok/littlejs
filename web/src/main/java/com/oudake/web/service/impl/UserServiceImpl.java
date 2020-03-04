package com.oudake.web.service.impl;

import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.common.utils.DateUtil;
import com.oudake.common.utils.IpUtil;
import com.oudake.web.dao.TblUserInfoMapper;
import com.oudake.web.dao.TblUserMapper;
import com.oudake.web.model.TblUser;
import com.oudake.web.model.TblUserInfo;
import com.oudake.web.service.SysCodeService;
import com.oudake.web.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wangyi
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger LOGGER = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private TblUserInfoMapper userInfoMapper;
    @Autowired
    private TblUserMapper userMapper;
    @Autowired
    private SysCodeService sysCodeService;

    @Override
    public TblUserInfo findInfoByUserId(Integer userId) {
        return userInfoMapper.findByUserId(userId);
    }

    @Override
    public ResultEntity addUserInfo(TblUserInfo userInfo) {
        // 币种
        String currency = sysCodeService.findSysCodeByTypeAndName(Constants.Currency.NAME, Constants.Currency.CNY).getDisplay1();
        userInfo.setCurrency(currency);
        // 注册时间(修改时间)
        userInfo.setRegisterTime(DateUtil.getNowTime());
        try {
            userInfoMapper.insert(userInfo);
        } catch (Exception e) {
            LOGGER.error(">>UserServiceImpl, 用户信息入库失败", e);
            return new ResultEntity(false, "用户信息入库失败");
        }
        return new ResultEntity(true, "添加用户信息成功");
    }

    @Override
    public ResultEntity editUserInfo(TblUserInfo userInfo) {
        Integer userId = userInfo.getUserId();
        Example example = new Example(TblUserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        userInfoMapper.updateByExampleSelective(userInfo, example);

        // 同时修改user表里的用户手机号
        String userPhone = userInfo.getUserPhone();
        if (userPhone != null && !"".equals(userPhone)) {
            example = new Example(TblUser.class);
            criteria = example.createCriteria();
            criteria.andEqualTo("userId", userId);

            TblUser user = new TblUser(userId);
            user.setUserPhone(userPhone);
            userMapper.updateByExampleSelective(user, example);
        }
        return new ResultEntity(true, "用户信息修改成功");
    }

    @Override
    public ResultEntity setLoginInfo(Integer userId, HttpServletRequest request) {
        TblUserInfo userInfo = new TblUserInfo();
        userInfo.setUserId(userId);
        userInfo.setLastLoginIp(IpUtil.getIp(request));
        userInfo.setLastLoginTime(DateUtil.getNowTime());
        return editUserInfo(userInfo);
    }
}
