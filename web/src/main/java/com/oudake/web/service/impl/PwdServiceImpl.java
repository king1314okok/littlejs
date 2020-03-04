package com.oudake.web.service.impl;

import com.oudake.common.ResultEntity;
import com.oudake.common.msg.MailMsg;
import com.oudake.web.dao.TblUserMapper;
import com.oudake.web.model.TblUser;
import com.oudake.web.model.TblUserInfo;
import com.oudake.web.mq.CoreSender;
import com.oudake.web.service.PwdService;
import com.oudake.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.text.MessageFormat;

/**
 * @author wangyi
 */
@Service
public class PwdServiceImpl implements PwdService {

    @Autowired
    private TblUserMapper userMapper;
    @Autowired
    private CoreSender coreSender;
    @Autowired
    private UserService userService;

    @Override
    public ResultEntity resetPwd(Integer userId, String newPwd) {
        Example example = new Example(TblUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", userId);
        TblUser user = new TblUser();
        user.setUserId(userId);
        user.setPassword(newPwd);
        userMapper.updateByExampleSelective(user, example);
        return new ResultEntity(true, "修改密码成功");
    }

    @Override
    public ResultEntity sendPwdMail(TblUser user, String captcha) {
        TblUserInfo userInfo = userService.findInfoByUserId(user.getUserId());
        if (userInfo == null) {
            return new ResultEntity(false, "该用户未绑定邮箱,请联系管理员");
        }
        if (userInfo.getUserEmail() == null) {
            return new ResultEntity(false, "该用户未绑定邮箱,请联系管理员");
        }
        MailMsg mailMsg = new MailMsg();
        mailMsg.setToAddr(userInfo.getUserEmail());
        mailMsg.setTitle("小金山花卉-找回密码");
        String context = MessageFormat.format("欢迎使用小金山花卉, 您的账户: {0} ,密码为: {1}<br>请及时修改密码, 以保证账户安全.", user.getUsername(), user.getPassword());
        mailMsg.setContext(context);
        mailMsg.setHtml(true);
        coreSender.sendMail(mailMsg);
        return new ResultEntity(true, "密码已发送到绑定邮箱,请注意查收");
    }
}
