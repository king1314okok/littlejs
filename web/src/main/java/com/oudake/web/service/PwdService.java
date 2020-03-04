package com.oudake.web.service;

import com.oudake.common.ResultEntity;
import com.oudake.web.model.TblUser;

/**
 * @author wangyi
 */
public interface PwdService {
    ResultEntity resetPwd(Integer userId, String newPwd);

    ResultEntity sendPwdMail(TblUser user, String captcha);
}
