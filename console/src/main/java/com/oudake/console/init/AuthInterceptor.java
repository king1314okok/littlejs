package com.oudake.console.init;

import com.oudake.console.controller.BaseController;
import com.oudake.console.model.TblSysUser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

/**
 * @Author wangyi
 * @Description AuthInterceptor
 * @Date 2019/1/24 18:29
 * @Version 1.0
 */
@Configuration
public class AuthInterceptor extends HandlerInterceptorAdapter {

    @Value("${server.port}")
    private String serverPort;

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws Exception {
        HttpSession currSession = request.getSession();

        // 会话是否过期
        if(currSession == null) {
            response.sendRedirect(request.getContextPath() + "/login");
            return false;
        }

        // 用户是否登录
        TblSysUser user = (TblSysUser) currSession.getAttribute(BaseController.SYS_USER_SESSION);
        if(user == null) {
            String url = request.getScheme() + "://" + request.getServerName() + ":" + serverPort + "/" + request.getContextPath() + "login";
            response.sendRedirect(url);
            return false;
        }
        return true;
    }
}
