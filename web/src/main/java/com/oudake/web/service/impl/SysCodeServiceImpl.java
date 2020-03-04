package com.oudake.web.service.impl;

import com.oudake.web.dao.TblSysCodeMapper;
import com.oudake.web.model.TblSysCode;
import com.oudake.web.service.SysCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author wangyi
 */
@Service
public class SysCodeServiceImpl implements SysCodeService {

    @Autowired
    private TblSysCodeMapper sysCodeMapper;

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
}
