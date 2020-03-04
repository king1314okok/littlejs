package com.oudake.console.service.impl;

import com.oudake.common.ResultEntity;
import com.oudake.common.msg.MailMsg;
import com.oudake.console.mq.CoreSender;
import com.oudake.console.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangyi
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private CoreSender coreSender;

    @Override
    public ResultEntity sendHtmlMail(String title, String context, String toAddr) {
        MailMsg mailMsg = new MailMsg();
        mailMsg.setTitle(title);
        mailMsg.setContext(context);
        mailMsg.setHtml(true);
        mailMsg.setToAddr(toAddr);
        coreSender.sendMail(mailMsg);
        return new ResultEntity(true, "邮件发送成功");
    }
}
