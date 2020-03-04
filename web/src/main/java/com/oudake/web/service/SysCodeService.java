package com.oudake.web.service;

import com.oudake.web.model.TblSysCode;

import java.util.List;

/**
 * @author wangyi
 */
public interface SysCodeService {
    List<TblSysCode> findSysCodeByType(String type);

    TblSysCode findSysCodeByTypeAndName(String type, String code);
}
