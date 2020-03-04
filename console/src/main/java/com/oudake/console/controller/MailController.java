package com.oudake.console.controller;

import com.oudake.common.ResultEntity;
import com.oudake.console.service.MailService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wangyi
 */
@RestController
@RequestMapping("mail")
public class MailController {

    private final static String MAIL_REGEX = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";

    @Autowired
    private MailService mailService;

    @GetMapping("")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("mail/mail");
        return modelAndView;
    }

    @PostMapping("sendMail")
    public ResultEntity sendMail(String toAddr, String title, String context) {
        if (StringUtils.isBlank(toAddr) || !toAddr.matches(MAIL_REGEX)) {
            return new ResultEntity(false, "请验证邮件地址是否正确");
        }
        if (StringUtils.isBlank(title)) {
            return new ResultEntity(false, "请输入邮件标题");
        }
        if (StringUtils.isBlank(context)) {
            return new ResultEntity(false, "请输入邮件内容");
        }
        return mailService.sendHtmlMail(title, context, toAddr);
    }

}
