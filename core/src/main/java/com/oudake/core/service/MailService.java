package com.oudake.core.service;

/**
 * @Author wangyi
 * @Description 邮件服务
 * @Date 2018/12/22 18:29
 * @Version 1.0
 */
public interface MailService {

    void sendMail(String email, String title, String text, boolean isHtml);
}
