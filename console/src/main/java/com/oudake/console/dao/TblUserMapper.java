package com.oudake.console.dao;

import com.oudake.console.model.TblUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyi
 */
public interface TblUserMapper extends Mapper<TblUser> {
    int insert(TblUser record);

    int insertSelective(TblUser record);

    List<TblUser> findUserByNameAndTypeAndStatus(@Param("user") TblUser user);
}