package com.oudake.console.dao;

import com.oudake.console.model.TblFlowerType;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyi
 */
public interface TblFlowerTypeMapper extends Mapper<TblFlowerType> {
    int insert(TblFlowerType record);

    int insertSelective(TblFlowerType record);

    List<TblFlowerType> findByFlowerType(@Param("flowerType") TblFlowerType flowerType);
}