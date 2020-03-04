package com.oudake.console.dao;

import com.oudake.console.model.TblFlowerStatus;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wangyi
 */
public interface TblFlowerStatusMapper extends Mapper<TblFlowerStatus> {
    int insert(TblFlowerStatus record);

    int insertSelective(TblFlowerStatus record);

    TblFlowerStatus findById_Type(@Param("flowerId") Integer flowerId, @Param("type") String type);

    Integer getAllViewCount(@Param("type") String type);
}