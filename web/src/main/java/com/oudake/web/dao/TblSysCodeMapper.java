package com.oudake.web.dao;

import com.oudake.web.model.TblSysCode;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wangyi
 */
public interface TblSysCodeMapper extends Mapper<TblSysCode> {
    int insert(TblSysCode record);

    int insertSelective(TblSysCode record);

    TblSysCode findSysCodeByTypeAndName(@Param("type") String type, @Param("code") String code);
}