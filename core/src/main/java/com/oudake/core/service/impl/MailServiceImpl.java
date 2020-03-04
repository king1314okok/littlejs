package com.oudake.core.service.impl;

import com.oudake.core.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author wangyi
 * @Description 邮件服务
 * @Date 2018/12/22 18:30
 * @Version 1.0
 */
@Service
public class MailServiceImpl implements MailService {

    private final static Logger LOGGER = LoggerFactory.getLogger(MailService.class);

    @Value("${spring.mail.username}")
    private String formAddr;

    @Autowired
    private JavaMailSender javaMailSender;


    /**
     * 发送邮件
     * @param email toAddr
     * @param title 标题
     * @param text 内容
     * @param isHtml 内容是否开启html格式
     */
    @Override
    public void sendMail(String email, String title, String text, boolean isHtml){
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(formAddr);
            helper.setTo(email);
            helper.setSubject(title);
            helper.setText(text, isHtml);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            LOGGER.error(">>发送邮件失败: ", e);
        }
    }
}
