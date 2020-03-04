package com.oudake.web.dao;

import com.oudake.web.model.TblFlowerCode;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyi
 */
public interface TblFlowerCodeMapper extends Mapper<TblFlowerCode> {
    int insert(TblFlowerCode record);

    int insertSelective(TblFlowerCode record);

    List<TblFlowerCode> findSearchCodes();
}