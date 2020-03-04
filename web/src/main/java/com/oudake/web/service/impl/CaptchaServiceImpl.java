package com.oudake.web.service.impl;

import com.oudake.common.ResultEntity;
import com.oudake.common.msg.MailMsg;
import com.oudake.web.mq.CoreSender;
import com.oudake.web.service.CaptchaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

/**
 * @author wangyi
 */
@Service
public class CaptchaServiceImpl implements CaptchaService {

    @Autowired
    private CoreSender coreSender;

    @Override
    public ResultEntity sendCaptchaMail(String toAddr, String captcha) {
        MailMsg mailMsg = new MailMsg();
        mailMsg.setToAddr(toAddr);
        mailMsg.setTitle("小金山花卉-验证码");
        String context = MessageFormat.format("欢迎使用小金山花卉, 您的验证码为 {0}.", captcha);
        mailMsg.setContext(context);
        mailMsg.setHtml(true);
        coreSender.sendMail(mailMsg);
        return new ResultEntity(true, "验证码发送成功,请注意查收");
    }
}
