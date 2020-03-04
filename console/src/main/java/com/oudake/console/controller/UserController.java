package com.oudake.console.controller;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblUser;
import com.oudake.console.model.TblUserInfo;
import com.oudake.console.service.UserService;
import com.oudake.console.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @author wangyi
 */
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("user/user");
        return modelAndView;
    }

    @GetMapping("userInfo")
    public ModelAndView userInfo(Integer userId, ModelAndView modelAndView) {
        modelAndView.setViewName("user/userInfo");
        if (userId == null) {
            modelAndView.addObject("errorMsg", "用户名为空");
            return modelAndView;
        }
        TblUser user = userService.findUserByUserId(userId);
        if (user == null) {
            modelAndView.addObject("errorMsg", "无此用户");
            return modelAndView;
        }
        TblUserInfo userInfo = userService.findInfoByUserId(userId);
        modelAndView.addObject("user", user);
        modelAndView.addObject("userInfo", userInfo);
        return modelAndView;
    }

    @PostMapping("dataGrid")
    public Layui dataGrid(@Valid Page page, BindingResult bindingResult,
                          TblUser user) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return Layui.error(validResult.getMsg());
        }
        return userService.findUserByPage(user, page);
    }

    @PostMapping("freeze")
    public ResultEntity freeze(Integer userId) {
        if (userId == null) {
            return new ResultEntity(false, "用户Id为空");
        }
        return userService.freezeUser(userId);
    }

    @PostMapping("recover")
    public ResultEntity recover(Integer userId) {
        if (userId == null) {
            return new ResultEntity(false, "用户Id为空");
        }
        return userService.recoverUser(userId);
    }
}
