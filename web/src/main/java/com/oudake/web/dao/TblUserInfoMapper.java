package com.oudake.web.dao;

import com.oudake.web.model.TblUserInfo;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wangyi
 */
public interface TblUserInfoMapper extends Mapper<TblUserInfo> {
    int insert(TblUserInfo record);

    int insertSelective(TblUserInfo record);

    TblUserInfo findByUserId(@Param("userId") Integer userId);
}