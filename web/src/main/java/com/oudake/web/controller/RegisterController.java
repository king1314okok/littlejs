package com.oudake.web.controller;

import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.common.utils.CaptchaUtil;
import com.oudake.web.model.TblUser;
import com.oudake.web.service.RegisterService;
import com.oudake.web.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author wangyi
 * @Description 用户注册
 * @Date 2018/12/20 9:09
 * @Version 1.0
 */
@Controller
public class RegisterController {

    @Autowired
    private RegisterService registerService;

    @RequestMapping("register")
    public String register() {
        return "register";
    }

    @RequestMapping("doRegister")
    @ResponseBody
    public ResultEntity doRegister(@Valid TblUser user, BindingResult bindingResult,
                                   String passwordAgain, String userPhone, String captcha,
                                   HttpServletRequest request) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        if (passwordAgain == null || "".equals(passwordAgain)) {
            return new ResultEntity(false, "请输入第二遍密码");
        }
        if (!passwordAgain.equals(user.getPassword())) {
            return new ResultEntity(false, "前后输入密码不一致");
        }
        if (userPhone == null || "".equals(userPhone)) {
            return new ResultEntity(false, "手机号不能为空");
        }
        if (StringUtils.isBlank(captcha)) {
            return new ResultEntity(false, "请输入验证码");
        }
        if (!CaptchaUtil.validate(request.getSession(), captcha)) {
            return new ResultEntity(false, "验证码错误");
        }
        return registerService.register(user);
    }

}
