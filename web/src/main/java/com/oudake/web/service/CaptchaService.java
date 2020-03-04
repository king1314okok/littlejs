package com.oudake.web.service;

import com.oudake.common.ResultEntity;

/**
 * @author wangyi
 */
public interface CaptchaService {
    ResultEntity sendCaptchaMail(String toAddr, String captcha);
}
