package com.oudake.console.service.impl;

import com.oudake.common.ResultEntity;
import com.oudake.console.dao.TblSysUserMapper;
import com.oudake.console.model.TblSysUser;
import com.oudake.console.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

/**
 * @Author wangyi
 * @Description SysUserServiceImpl
 * @Date 2019/1/23 19:45
 * @Version 1.0
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private TblSysUserMapper sysUserMapper;

    @Override
    public TblSysUser findById(String userId) {
        TblSysUser sysUser = new TblSysUser();
        sysUser.setUserId(Integer.parseInt(userId));
        return sysUserMapper.selectOne(sysUser);
    }

    @Override
    public ResultEntity resetPwd(TblSysUser sysUser) {
        Example example = new Example(TblSysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", sysUser.getUserId());
        sysUserMapper.updateByExampleSelective(sysUser, example);
        return new ResultEntity(true, "修改密码成功");
    }

    @Override
    public ResultEntity updateSysUserInfo(TblSysUser sysUser) {
        Example example = new Example(TblSysUser.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId", sysUser.getUserId());
        sysUserMapper.updateByExampleSelective(sysUser, example);
        return new ResultEntity(true, "修改信息成功");
    }
}
