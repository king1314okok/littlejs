package com.oudake.core.dao;

import com.oudake.core.model.TblFlower;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyi
 */
public interface TblFlowerMapper extends Mapper<TblFlower> {
    int insert(TblFlower record);

    int insertSelective(TblFlower record);

    List<TblFlower> findByFlowerNameAndTypeName(@Param("flower") TblFlower flower);

    List<TblFlower> findByFlowerName(@Param("flowerName") String flowerName);

    List<TblFlower> findByTypeName(@Param("typeName") String typeName);
}