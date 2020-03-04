package com.oudake.web.controller;

import com.oudake.common.ResultEntity;
import com.oudake.web.model.TblFlowerCode;
import com.oudake.web.model.TblUser;
import com.oudake.web.service.FlowerCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author wangyi
 */
@Controller
public class BaseController {

    private static final String CODE_LIST_NAME = "codeList";

    @Autowired
    private FlowerCodeService flowerCodeService;

    /**
     * 获取登录后的当前用户
     * @param request request
     * @return user
     */
    public TblUser getCurrentUser(HttpServletRequest request) {
        TblUser user = (TblUser)request.getSession().getAttribute("user");
        return user;
    }

    /**
     * 登出用户
     * @param request request
     */
    public void userLogout(HttpServletRequest request) {
        request.getSession().removeAttribute("user");
    }

    /**
     * 检查是否登录
     * @param request request
     * @return
     */
    public ResultEntity isLogin(HttpServletRequest request) {
        TblUser user = (TblUser) request.getSession().getAttribute("user");
        if (user == null) {
            return new ResultEntity(false, "请先登录");
        } else {
            return new ResultEntity(true, "已登录");
        }
    }

    /**
     * 获取搜索条件
     * @param request request
     */
    public void setCodeList(HttpServletRequest request) {
        // 查找搜索条件
        HttpSession session = request.getSession();
        List<TblFlowerCode> codeList = (List<TblFlowerCode>) session.getAttribute(CODE_LIST_NAME);
        if (codeList == null) {
            codeList = flowerCodeService.findSearchCodes();
        }
        session.setAttribute(CODE_LIST_NAME, codeList);
    }

}
