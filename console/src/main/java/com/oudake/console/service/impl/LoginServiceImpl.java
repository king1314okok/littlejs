package com.oudake.console.service.impl;

import com.oudake.common.ResultEntity;
import com.oudake.console.dao.TblSysUserMapper;
import com.oudake.console.model.TblSysUser;
import com.oudake.console.service.LoginService;
import com.oudake.console.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.MessageFormat;
import java.util.List;

/**
 * @author wangyi
 */
@Service
public class LoginServiceImpl implements LoginService {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Autowired
    private TblSysUserMapper sysUserMapper;

    @Autowired
    private MailService mailService;

    /**
     * 根据用户名查找用户
     * @param username username
     * @return user
     */
    @Override
    public TblSysUser findByUsername(String username) {
        Example example = new Example(TblSysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", username);
        List<TblSysUser> users = sysUserMapper.selectByExample(example);
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
        TblSysUser user = findByUsername(username);
        if (user == null) {
            return new ResultEntity(false, "登录失败, 用户名不存在");
        } else {
            if (!user.getPassword().equals(password)) {
                return new ResultEntity(false, "登录失败, 密码错误");
            } else {
                return new ResultEntity(true, "登录成功", user);
            }
        }
    }

    @Override
    public ResultEntity findPwd(String username, String userEmail) {
        TblSysUser sysUser = findByUsername(username);
        if (sysUser == null) {
            return new ResultEntity(false, "该用户不存在");
        }
        if (sysUser.getUserEmail() == null || !sysUser.getUserEmail().equals(userEmail)) {
            return new ResultEntity(false, "该邮箱非原绑定的邮箱");
        }
        String title = "小金山花卉-管理员找回密码";
        String context = MessageFormat.format("欢迎使用小金山花卉销售系统控制台, 您的密码为 {0} ,请牢记您的密码.", sysUser.getPassword());
        mailService.sendHtmlMail(title, context, sysUser.getUserEmail());
        return new ResultEntity(true, "密码已发至您的邮箱");
    }
}
