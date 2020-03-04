package com.oudake.console.service;

import com.oudake.common.ResultEntity;

/**
 * @author wangyi
 */
public interface MailService {
    ResultEntity sendHtmlMail(String title, String context, String toAddr);
}
