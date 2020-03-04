package com.oudake.web.controller;

import com.oudake.common.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wangyi
 */
@Controller
public class CaptchaController {

    @RequestMapping("captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) {
        CaptchaUtil.render(request, response);
    }

}
