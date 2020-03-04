package com.oudake.web.controller;

import com.oudake.common.ResultEntity;
import com.oudake.common.utils.IpUtil;
import com.oudake.web.model.TblUser;
import com.oudake.web.model.TblUserInfo;
import com.oudake.web.service.UserService;
import com.oudake.web.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Author wangyi
 * @Description UserController
 * @Date 2019/2/8 11:53
 * @Version 1.0
 */
@Controller
@RequestMapping("user")
public class UserController extends BaseController {

    @Autowired
    private UserService userService;

    @RequestMapping("")
    public String index() {
        return "user";
    }

    @RequestMapping("payPwdForm")
    public String payPwdForm(Model model) {
        model.addAttribute("url", "/user/changePayPwd");
        return "payPwdForm";
    }

    @RequestMapping("userInfo")
    public ModelAndView userInfo(HttpServletRequest request, ModelAndView modelAndView) {
        TblUserInfo userInfo = userService.findInfoByUserId(getCurrentUser(request).getUserId());
        if (userInfo == null) {
            modelAndView.addObject("msg", "该用户信息不存在");
        } else {
            modelAndView.addObject("userInfo", userInfo);
        }
        modelAndView.setViewName("user/userInfo");
        return modelAndView;
    }

    @RequestMapping("editUserInfo")
    @ResponseBody
    public ResultEntity editUserInfo(@Valid TblUserInfo userInfo, BindingResult bindingResult, HttpServletRequest request) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        // 用户id
        userInfo.setUserId(getCurrentUser(request).getUserId());
        // ip
        String ip = IpUtil.getIp(request);
        userInfo.setRegisterIp(ip);
        TblUserInfo info = userService.findInfoByUserId(getCurrentUser(request).getUserId());
        if (info == null) {
            return userService.addUserInfo(userInfo);
        } else {
            return userService.editUserInfo(userInfo);
        }
    }

    @RequestMapping("changePayPwd")
    @ResponseBody
    public ResultEntity changePayPwd(String payPwd, HttpServletRequest request) {
        if (payPwd == null || "".equals(payPwd)) {
            return new ResultEntity(false, "请输入支付密码");
        }
        if (payPwd.length() != 6) {
            return new ResultEntity(false, "支付密码必须为6位");
        }
        TblUserInfo userInfo = userService.findInfoByUserId(getCurrentUser(request).getUserId());
        if (userInfo == null) {
            return new ResultEntity(false, "请先添加用户信息");
        }
        // ip
        String ip = IpUtil.getIp(request);
        userInfo.setRegisterIp(ip);
        userInfo.setPayPwd(payPwd);
        ResultEntity result = userService.editUserInfo(userInfo);
        result.setMsg("支付密码修改成功");
        return result;
    }
}
