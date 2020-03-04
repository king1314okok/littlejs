package com.oudake.console.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.common.utils.DateUtil;
import com.oudake.console.dao.TblUserInfoMapper;
import com.oudake.console.dao.TblUserMapper;
import com.oudake.console.model.TblUser;
import com.oudake.console.model.TblUserInfo;
import com.oudake.console.service.SysCodeService;
import com.oudake.console.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyi
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private TblUserMapper userMapper;
    @Autowired
    private TblUserInfoMapper userInfoMapper;

    @Autowired
    private SysCodeService sysCodeService;

    @Override
    public Layui findUserByPage(TblUser user, Page page) {
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        List<TblUser> list = userMapper.findUserByNameAndTypeAndStatus(user);
        PageInfo<TblUser> pageInfo = new PageInfo<>(list);
        Layui layui = new Layui();
        return layui.data((int)pageInfo.getTotal(), list, "userList");
    }

    @Override
    public TblUser findUserByUserId(Integer userId) {
        Example example = new Example(TblUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        return userMapper.selectOneByExample(example);
    }

    @Override
    public TblUserInfo findInfoByUserId(Integer userId) {
        Example example = new Example(TblUserInfo.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        return userInfoMapper.selectOneByExample(example);
    }

    @Override
    public ResultEntity freezeUser(Integer userId) {
        Example example = new Example(TblUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);

        TblUser user = new TblUser();
        String userStatus = sysCodeService.findSysCodeByTypeAndName(Constants.UserStatus.NAME, Constants.UserStatus.STOP).getDisplay1();
        user.setUserStatus(userStatus);

        int result = userMapper.updateByExampleSelective(user, example);
        if (result == 0) {
            return new ResultEntity(false, "停用失败");
        } else {
            return new ResultEntity(true, "停用成功");
        }
    }

    @Override
    public ResultEntity recoverUser(Integer userId) {
        Example example = new Example(TblUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);

        TblUser user = new TblUser();
        String userStatus = sysCodeService.findSysCodeByTypeAndName(Constants.UserStatus.NAME, Constants.UserStatus.NORMAL).getDisplay1();
        user.setUserStatus(userStatus);

        int result = userMapper.updateByExampleSelective(user, example);
        if (result == 0) {
            return new ResultEntity(false, "恢复失败");
        } else {
            return new ResultEntity(true, "恢复成功");
        }
    }

    @Override
    public List<TblUserInfo> getActiveUser() {
        List<TblUserInfo> list = userInfoMapper.getActiveUser();
        for (TblUserInfo userInfo : list) {
            userInfo.setLastLoginTime(DateUtil.changeDayByFormat(userInfo.getLastLoginTime(), "yyyyMMddHHmmSS", "yyyy-MM-dd  HH:mm:SS"));
        }
        return list;
    }

    @Override
    public Integer getMonthActiveUserAmount() {
        // 获取当前年月,如201903
        String month = DateUtil.getMonth();
        List<TblUserInfo> list = userInfoMapper.findUserByLoginDate(month);
        return list.size();
    }

    @Override
    public Integer getLastMonthActiveUserAmount() {
        // 获取上一个年月,如201902
        String month = DateUtil.getMonth(-1);
        List<TblUserInfo> list = userInfoMapper.findUserByLoginDate(month);
        return list.size();
    }
}
