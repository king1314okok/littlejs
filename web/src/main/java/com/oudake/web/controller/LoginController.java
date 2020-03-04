package com.oudake.web.controller;

import com.oudake.common.ResultEntity;
import com.oudake.web.model.TblUser;
import com.oudake.web.service.LoginService;
import com.oudake.web.service.UserService;
import com.oudake.web.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @Author wangyi
 * @Description 用户登录
 * @Date 2018/12/20 9:18
 * @Version 1.0
 */
@Controller
public class LoginController extends BaseController {

    private final static Logger LOGGER = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;

    @RequestMapping("login")
    public String login() {
        return "login";
    }

    /**
     * 登录校验
     * @param user user
     * @param bindingResult bindingResult
     * @param request request
     * @return resultEntity
     */
    @RequestMapping("doLogin")
    @ResponseBody
    public ResultEntity doLogin(@Valid TblUser user, BindingResult bindingResult,
                                HttpServletRequest request, HttpServletResponse response) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        ResultEntity resultEntity = loginService.login(user.getUsername(), user.getPassword());
        if (resultEntity.isSuccess()) {
            TblUser userResult = (TblUser)resultEntity.getObj();
            userService.setLoginInfo(userResult.getUserId(), request);
            request.getSession().setAttribute("user", userResult);
        }
        setCodeList(request);
        return resultEntity;
    }

    @RequestMapping("logout")
    public String logout(HttpServletRequest request) {
        userLogout(request);
        return "redirect:/";
    }
}
