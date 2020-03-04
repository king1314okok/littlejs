package com.oudake.console.service;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblSysCode;

import java.util.List;

/**
 * @Author wangyi
 * @Description SysCodeService
 * @Date 2019/2/4 17:59
 * @Version 1.0
 */
public interface SysCodeService {
    Layui findSysCodeByPage(TblSysCode sysCode, Page page);

    TblSysCode findSysCodeByTypeAndName(String type, String code);

    List<TblSysCode> findSysCodeByType(String type);

    boolean isSysCodeExist(String type, String code);

    ResultEntity addSysCode(TblSysCode sysCode);

    ResultEntity editSysCode(TblSysCode sysCode);

    ResultEntity delSysCode(String type, String code);
}
