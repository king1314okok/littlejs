package com.oudake.web.controller;

import com.oudake.common.ResultEntity;
import com.oudake.web.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author wangyi
 * @Description 邮件发送
 * @Date 2018/12/22 18:07
 * @Version 1.0
 */
@Controller("mail")
public class MailController {

    @Autowired
    private CaptchaService captchaService;

    @RequestMapping("/captchaMail")
    @ResponseBody
    public ResultEntity captchaMail() {
        return null;
    }

}
