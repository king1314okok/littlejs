package com.oudake.console.dao;

import com.oudake.console.model.TblSysUser;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wangyi
 */
public interface TblSysUserMapper extends Mapper<TblSysUser> {
    int insert(TblSysUser record);

    int insertSelective(TblSysUser record);
}