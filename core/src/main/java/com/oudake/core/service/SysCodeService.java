package com.oudake.core.service;

import com.oudake.core.model.TblSysCode;

import java.util.List;

/**
 * @author wangyi
 */
public interface SysCodeService {
    List<TblSysCode> findSysCodeByType(String type);

    TblSysCode findSysCodeByTypeAndName(String type, String code);
}
