package com.oudake.console.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.dao.TblFlowerCodeMapper;
import com.oudake.console.model.TblFlowerCode;
import com.oudake.console.service.FlowerCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author wangyi
 * @Description FlowerCodeServiceImpl
 * @Date 2019/1/15 11:45
 * @Version 1.0
 */
@Service
public class FlowerCodeServiceImpl implements FlowerCodeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(FlowerCodeServiceImpl.class);

    @Autowired
    private TblFlowerCodeMapper flowerCodeMapper;

    @Override
    public Layui findCodeByPage(TblFlowerCode flowerCode, Page page) {
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        Example example = new Example(TblFlowerCode.class);
        Example.Criteria criteria = example.createCriteria();

        if (flowerCode.getTypeName() != null && !"".equals(flowerCode.getTypeName())) {
            criteria.andLike("typeName", "%" + flowerCode.getTypeName() + "%");
        }
        if (flowerCode.getFatherName() != null && !"".equals(flowerCode.getFatherName())) {
            criteria.andLike("fatherName", "%" + flowerCode.getFatherName() + "%");
        }

        example.setOrderByClause("TYPE_ID asc");
        List<TblFlowerCode> list = flowerCodeMapper.selectByExample(example);
        PageInfo<TblFlowerCode> pageInfo = new PageInfo<>(list);
        Layui layui = new Layui();
        return layui.data((int)pageInfo.getTotal(), list, "flowerCodeList");
    }

    @Override
    public TblFlowerCode findCodeById(String typeId) {
        Example example = new Example(TblFlowerCode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("typeId", Integer.valueOf(typeId));
        return flowerCodeMapper.selectOneByExample(example);
    }

    @Override
    public boolean isCodeExist(String typeName) {
        Example example = new Example(TblFlowerCode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("typeName", typeName);
        return flowerCodeMapper.selectByExample(example).size() != 0;
    }

    /**
     * @Author wangyi
     * @Description // 添加花卉种类
     * @Date 2019/1/15
     * @Param [flowerCode]
     * @return resultEntity
    **/
    @Override
    public ResultEntity addCode(TblFlowerCode flowerCode) {
        return new ResultEntity(true, "添加种类成功", flowerCodeMapper.insert(flowerCode));
    }

    @Override
    public ResultEntity delCode(Integer typeId) {
        Example example = new Example(TblFlowerCode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("typeId", typeId);
        flowerCodeMapper.deleteByExample(example);
        return new ResultEntity(true, "删除成功");
    }

    /**
     * 修改花卉种类
     * @param flowerCode flower
     * @return resultEntity
     */
    @Override
    public ResultEntity editCode(TblFlowerCode flowerCode) {
        Example example = new Example(TblFlowerCode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("typeId", flowerCode.getTypeId());
        int result = flowerCodeMapper.updateByExampleSelective(flowerCode, example);
        return new ResultEntity(true, "修改种类成功", result);
    }

}
