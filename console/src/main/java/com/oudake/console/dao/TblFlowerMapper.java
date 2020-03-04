package com.oudake.console.dao;

import com.oudake.console.model.TblFlower;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyi
 */
public interface TblFlowerMapper extends Mapper<TblFlower> {
    int insert(TblFlower record);

    int insertSelective(TblFlower record);

    List<TblFlower> findByFlowerNameAndTypeName(@Param("flower")TblFlower flower);

    List<TblFlower> findById(@Param("flowerId") String flowerId);
}