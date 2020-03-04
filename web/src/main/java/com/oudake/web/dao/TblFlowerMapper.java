package com.oudake.web.dao;

import com.oudake.web.model.TblFlower;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyi
 */
public interface TblFlowerMapper extends Mapper<TblFlower> {
    int insert(TblFlower record);

    int insertSelective(TblFlower record);

    List<TblFlower> findByFlowerName(@Param("flowerName") String flowerName);

    List<TblFlower> findByTypeName(@Param("typeName") String typeName);

    List<TblFlower> findIndexFlower();
}