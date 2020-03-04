package com.oudake.console.dao;

import com.oudake.console.model.TblFlowerCode;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wangyi
 */
public interface TblFlowerCodeMapper extends Mapper<TblFlowerCode> {
    int insert(TblFlowerCode record);

    int insertSelective(TblFlowerCode record);
}