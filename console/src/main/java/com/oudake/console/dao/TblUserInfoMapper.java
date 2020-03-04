package com.oudake.console.dao;

import com.oudake.console.model.TblUser;
import com.oudake.console.model.TblUserInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyi
 */
public interface TblUserInfoMapper extends Mapper<TblUserInfo> {
    int insert(TblUserInfo record);

    int insertSelective(TblUserInfo record);

    List<TblUserInfo> getActiveUser();

    List<TblUserInfo> findUserByLoginDate(@Param("date") String date);
}