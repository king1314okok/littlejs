package com.oudake.web.service.impl;

import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.web.dao.TblUserMapper;
import com.oudake.web.model.TblUser;
import com.oudake.web.service.LoginService;
import com.oudake.web.service.SysCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author wangyi
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private TblUserMapper userMapper;

    @Autowired
    private SysCodeService sysCodeService;

    /**
     * 根据用户名查找用户
     * @param username username
     * @return user
     */
    @Override
    public TblUser findByUsername(String username) {
        Example example = new Example(TblUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<TblUser> users = userMapper.selectByExample(example);
        if (users.size() == 0) {
            return null;
        } else {
            return users.get(0);
        }
    }

    /**
     * 根据用户名判断用户是否存在
     * @param username username
     * @return boolean
     */
    @Override
    public boolean isUserExist(String username) {
        return findByUsername(username) != null;
    }

    /**
     * 验证用户名与密码是否匹配
     * @param username username
     * @param password password
     * @return resultEntity
     */
    @Override
    public ResultEntity login(String username, String password) {
        TblUser user = findByUsername(username);
        if (user == null) {
            return new ResultEntity(false, "登录失败, 用户名不存在");
        } else {
            String userStatus = sysCodeService.findSysCodeByTypeAndName(Constants.UserStatus.NAME, Constants.UserStatus.STOP).getDisplay1();
            if (userStatus.equals(user.getUserStatus())) {
                return new ResultEntity(false, "该用户已被停用, 请联系管理员");
            }
            if (!user.getPassword().equals(password)) {
                return new ResultEntity(false, "密码错误");
            } else {
                return new ResultEntity(true, "登录成功", user);
            }
        }
    }
}
