package com.oudake.console.service;

import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblSysUser;

/**
 * @Author wangyi
 * @Description SysUserService
 * @Date 2019/1/23 19:44
 * @Version 1.0
 */
public interface SysUserService {
    TblSysUser findById(String userId);

    ResultEntity resetPwd(TblSysUser sysUser);

    ResultEntity updateSysUserInfo(TblSysUser sysUser);
}
