package com.oudake.console.controller;

import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblSysUser;
import com.oudake.console.service.LoginService;
import com.oudake.console.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @Author wangyi
 * @Description 登录Controller
 * @Date 2019/1/3 18:52
 * @Version 1.0
 */
@RestController
public class LoginController extends BaseController {

    @Autowired
    private LoginService loginService;

    @GetMapping("login")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @GetMapping("findPwd")
    public ModelAndView findPwd(ModelAndView modelAndView) {
        modelAndView.setViewName("/sysUser/findPwd");
        return modelAndView;
    }

    @PostMapping("doLogin")
    public ResultEntity doLogin(@Valid TblSysUser user, BindingResult bindingResult, Integer rememberPwd,
                                HttpServletRequest request, HttpServletResponse response) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        ResultEntity resultEntity = loginService.login(user.getUsername(), user.getPassword());
        if (resultEntity.isSuccess()) {
            request.getSession().setAttribute(SYS_USER_SESSION, (TblSysUser)resultEntity.getObj());
        }
        return resultEntity;
    }

    @GetMapping("logout")
    public ModelAndView logout(ModelAndView modelAndView, HttpSession session) {
        session.removeAttribute(SYS_USER_SESSION);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping("doFindPwd")
    public ResultEntity doFindPwd(String username, String userEmail) {
        if (StringUtils.isBlank(username)) {
            return new ResultEntity(false, "请输入用户名");
        }
        if (StringUtils.isBlank(userEmail)) {
            return new ResultEntity(false, "请输入绑定的邮箱号");
        }
        return loginService.findPwd(username, userEmail);
    }

}
