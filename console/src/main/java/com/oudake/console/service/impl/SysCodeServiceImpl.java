package com.oudake.console.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.dao.TblSysCodeMapper;
import com.oudake.console.model.TblSysCode;
import com.oudake.console.service.SysCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author wangyi
 * @Description SysCodeServiceImpl
 * @Date 2019/2/4 18:00
 * @Version 1.0
 */
@Service
public class SysCodeServiceImpl implements SysCodeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(SysCodeServiceImpl.class);

    @Autowired
    private TblSysCodeMapper sysCodeMapper;

    @Override
    public Layui findSysCodeByPage(TblSysCode sysCode, Page page) {
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        List<TblSysCode> list = sysCodeMapper.findSysCodeByPage(sysCode);
        PageInfo<TblSysCode> pageInfo = new PageInfo<>(list);
        Layui layui = new Layui();
        return layui.data((int)pageInfo.getTotal(), list, "sysCodeList");
    }

    /**
     * 具体查找某一个sysCode
     * @param type type
     * @param code code
     * @return tblSysCode
     */
    @Override
    public TblSysCode findSysCodeByTypeAndName(String type, String code) {
        return sysCodeMapper.findSysCodeByTypeAndName(type, code);
    }

    /**
     * 根据type来查找其类型下的参数
     * @param type type
     * @return
     */
    @Override
    public List<TblSysCode> findSysCodeByType(String type) {
        Example example = new Example(TblSysCode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", type);
        return sysCodeMapper.selectByExample(example);
    }

    @Override
    public boolean isSysCodeExist(String type, String code) {
        return findSysCodeByTypeAndName(type, code) != null;
    }

    @Override
    public ResultEntity addSysCode(TblSysCode sysCode) {
        if (isSysCodeExist(sysCode.getType(), sysCode.getCode())) {
            return new ResultEntity(false, "该系统参数已存在");
        }
        int result = 0;
        try {
            result = sysCodeMapper.insertSelective(sysCode);
        } catch (Exception e) {
            LOGGER.error(">>sysCodeImpl, 添加参数失败", e);
            return new ResultEntity(false, "添加系统参数失败", result);
        }
        return new ResultEntity(true, "添加系统参数成功", result);
    }

    @Override
    public ResultEntity editSysCode(TblSysCode sysCode) {
        Example example = new Example(TblSysCode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", sysCode.getType());
        criteria.andEqualTo("code", sysCode.getCode());
        int result = 0;

        try {
            result = sysCodeMapper.updateByExampleSelective(sysCode, example);
        } catch (Exception e) {
            LOGGER.error(">>SysCodeServiceImpl, 修改系统参数失败", e);
            return new ResultEntity(false, "修改系统参数失败, 请检查参数", result);
        }
        return new ResultEntity(true, "修改系统参数成功", result);
    }

    @Override
    public ResultEntity delSysCode(String type, String code) {
        Example example = new Example(TblSysCode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("type", type);
        criteria.andEqualTo("code", code);

        int result = sysCodeMapper.deleteByExample(example);
        if (result == 0) {
            return new ResultEntity(false, "删除系统参数失败", result);
        } else {
            return new ResultEntity(true, "删除系统参数成功", result);
        }
    }
}
