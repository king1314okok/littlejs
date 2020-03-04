package com.oudake.console.dao;

import com.oudake.console.model.TblSysCode;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyi
 */
public interface TblSysCodeMapper extends Mapper<TblSysCode> {
    int insert(TblSysCode record);

    int insertSelective(TblSysCode record);

    List<TblSysCode> findSysCodeByPage(@Param("sysCode") TblSysCode sysCode);

    TblSysCode findSysCodeByTypeAndName(@Param("type") String type, @Param("code") String code);
}