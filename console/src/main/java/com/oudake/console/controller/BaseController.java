package com.oudake.console.controller;

import com.oudake.console.model.TblSysUser;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author wangyi
 * @Description BaseController
 * @Date 2019/1/23 19:52
 * @Version 1.0
 */
@Controller
public class BaseController {

    public final static String SYS_USER_SESSION = "sysUser";

    /**
     * 获取登录后的当前用户
     * @param request request
     * @return user
     */
    public TblSysUser getCurrentUser(HttpServletRequest request) {
        TblSysUser user = (TblSysUser)request.getSession().getAttribute("sysUser");
        return user;
    }

}
