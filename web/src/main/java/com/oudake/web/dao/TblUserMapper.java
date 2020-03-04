package com.oudake.web.dao;

import com.oudake.web.model.TblUser;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wangyi
 */
public interface TblUserMapper extends Mapper<TblUser> {

    int insert(TblUser record);

    int insertSelective(TblUser record);

    TblUser findUserByUsername(@Param("username") String username);
}