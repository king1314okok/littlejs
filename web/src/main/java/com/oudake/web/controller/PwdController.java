package com.oudake.web.controller;

import com.oudake.common.ResultEntity;
import com.oudake.common.utils.CaptchaUtil;
import com.oudake.web.model.TblUser;
import com.oudake.web.service.LoginService;
import com.oudake.web.service.PwdService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wangyi
 */
@RestController
public class PwdController extends BaseController {

    @Autowired
    private LoginService loginService;
    @Autowired
    private PwdService pwdService;

    /**
     * 修改密码
     * @param modelAndView modelAndView
     * @return modelAndView
     */
    @GetMapping("resetPwd")
    public ModelAndView resetPwd(ModelAndView modelAndView) {
        modelAndView.setViewName("resetPwd");
        return modelAndView;
    }

    /**
     * 找回密码
     * @param modelAndView modelAndView
     * @return modelAndView
     */
    @GetMapping("findPwd")
    public ModelAndView findPwd(ModelAndView modelAndView) {
        modelAndView.setViewName("findPwd");
        return modelAndView;
    }

    @PostMapping("doResetPwd")
    public ResultEntity doResetPwd(String oldPwd, String pwd, String pwdAgain,
                                   String captcha, HttpServletRequest request) {
        if (StringUtils.isBlank(oldPwd)) {
            return new ResultEntity(false, "请输入旧密码");
        }
        if (StringUtils.isBlank(pwd)) {
            return new ResultEntity(false, "请输入新密码");
        }
        if (StringUtils.isBlank(pwdAgain)) {
            return new ResultEntity(false, "请再次输入密码");
        }
        if (!pwd.equals(pwdAgain)) {
            return new ResultEntity(false, "前后输入密码不一致");
        }
        if (StringUtils.isBlank(captcha)) {
            return new ResultEntity(false, "请输入验证码");
        }
        if (!CaptchaUtil.validate(request.getSession(), captcha)) {
            return new ResultEntity(false, "验证码错误");
        }
        TblUser user = getCurrentUser(request);
        if (!user.getPassword().equals(oldPwd)) {
            return new ResultEntity(false, "旧密码错误");
        }
        ResultEntity result = pwdService.resetPwd(user.getUserId(), pwd);
        if (result.isSuccess()) {
            userLogout(request);
        }
        return result;
    }

    /**
     * 邮箱找回密码
     * @param username username
     * @param captcha captcha
     * @param userPhone userPhone
     * @param session session
     * @return ResultEntity
     */
    @PostMapping("doFindPwd")
    public ResultEntity doFindPwd(String username, String captcha, String userPhone, HttpSession session) {
        if (StringUtils.isBlank(username)) {
            return new ResultEntity(false, "请输入用户名");
        }
        if (StringUtils.isBlank(userPhone)) {
            return new ResultEntity(false, "请输入手机号");
        }
        if (StringUtils.isBlank(captcha)) {
            return new ResultEntity(false, "请输入验证码");
        }
        if (!CaptchaUtil.validate(session, captcha)) {
            return new ResultEntity(false, "验证码错误");
        }
        TblUser user = loginService.findByUsername(username);
        if (user == null) {
            return new ResultEntity(false, "该用户不存在");
        }
        if (!user.getUserPhone().equals(userPhone)) {
            return new ResultEntity(false, "绑定的手机不一致");
        }
        return pwdService.sendPwdMail(user, captcha);
    }
}
