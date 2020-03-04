package com.oudake.web.dao;

import com.oudake.web.model.TblFlowerStatus;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wangyi
 */
public interface TblFlowerStatusMapper extends Mapper<TblFlowerStatus> {
    int insert(TblFlowerStatus record);

    int insertSelective(TblFlowerStatus record);

    TblFlowerStatus findByIdAndType(@Param("flowerId") Integer flowerId, @Param("type") String type);
}