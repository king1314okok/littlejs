package com.oudake.console.controller;

import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblSysUser;
import com.oudake.console.service.SysUserService;
import com.oudake.console.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @Author wangyi
 * @Description SysUserController
 * @Date 2019/1/23 19:33
 * @Version 1.0
 */
@Controller
@RequestMapping("sysUser")
public class SysUserController extends BaseController {

    @Autowired
    private SysUserService sysUserService;

    @RequestMapping("userform")
    public String viewUserInfo(String userId, Model model, HttpSession session) {
        if (StringUtils.isBlank(userId)) {
            return ResultUtil.errorModel("userId为空", model);
        }
        session.setAttribute(SYS_USER_SESSION, sysUserService.findById(userId));
        return "sysUser/userform";
    }

    @RequestMapping("updateSysUserInfo")
    @ResponseBody
    public ResultEntity updateSysUserInfo(TblSysUser sysUser) {
        if (sysUser == null || sysUser.getUserId() == null) {
            return new ResultEntity(false, "用户Id不能为空");
        }
        return sysUserService.updateSysUserInfo(sysUser);
    }

    @RequestMapping("password")
    public String password(String userId, Model model) {
        if (StringUtils.isBlank(userId)) {
            model.addAttribute("errorMsg", "userId为空");
            return ResultUtil.errorModel("userId为空", model);
        }
        return "sysUser/password";
    }

    @RequestMapping("resetPwd")
    @ResponseBody
    public ResultEntity resetPwd(String oldPassword, String password, String repassword, HttpServletRequest request) {
        if (StringUtils.isBlank(oldPassword)) {
            return new ResultEntity(false, "请输入原密码");
        }
        if (StringUtils.isBlank(password)) {
            return new ResultEntity(false, "请输入新密码");
        }
        if (StringUtils.isBlank(repassword)) {
            return new ResultEntity(false, "请再次输入密码");
        }
        TblSysUser sysUser = new TblSysUser();
        sysUser.setUserId(getCurrentUser(request).getUserId());
        sysUser.setPassword(password);
        ResultEntity result = sysUserService.resetPwd(sysUser);
        if (result.isSuccess()) {
            request.getSession().removeAttribute(SYS_USER_SESSION);
        }
        return result;
    }
}
