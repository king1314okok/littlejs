package com.oudake.console.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.dao.TblFlowerTypeMapper;
import com.oudake.console.model.TblFlower;
import com.oudake.console.model.TblFlowerType;
import com.oudake.console.service.FlowerCodeService;
import com.oudake.console.service.FlowerService;
import com.oudake.console.service.FlowerTypeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author wangyi
 */
@Service
public class FlowerTypeServiceImpl implements FlowerTypeService {

    private final static Logger LOGGER = LoggerFactory.getLogger(FlowerTypeServiceImpl.class);

    @Autowired
    private TblFlowerTypeMapper flowerTypeMapper;

    @Autowired
    private FlowerService flowerService;
    @Autowired
    private FlowerCodeService flowerCodeService;

    /**
     * layui table, 查询条件在sql中
     * @param flowerType flowerType
     * @param page page
     * @return Layui
     */
    @Override
    public Layui findTypeByPage(TblFlowerType flowerType, Page page) {
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        List<TblFlowerType> list = flowerTypeMapper.findByFlowerType(flowerType);
        PageInfo<TblFlowerType> pageInfo = new PageInfo<>(list);
        Layui layui = new Layui();
        return layui.data((int)pageInfo.getTotal(), list, "flowerTypeList");
    }

    /**
     * 根据花名,查找花卉id,再把flowerId跟typeName入库
     * @param flowerType flowerName
     * @return resultEntity
     */
    @Override
    public ResultEntity addFlowerType(TblFlowerType flowerType) {
        ResultEntity flowerIdResult = flowerService.findFlowerIdByName(flowerType.getFlowerName());
        if (!flowerIdResult.isSuccess()) {
            return flowerIdResult;
        }
        if (!flowerCodeService.isCodeExist(flowerType.getTypeName())) {
            return new ResultEntity(false, "该种类不存在");
        }
        flowerType.setFlowerId((Integer) flowerIdResult.getObj());
        int result = 0;

        try {
            result = flowerTypeMapper.insertSelective(flowerType);
        } catch (Exception e) {
            LOGGER.error(">>FlowerTypeServiceImpl, 添加花类失败", e);
            return new ResultEntity(false, "添加花类失败, 请检查参数", result);
        }

        return new  ResultEntity(true, "添加花类成功", result);
    }

    @Override
    public ResultEntity delFlowerType(TblFlowerType flowerType) {
        List<TblFlower> list = flowerService.findFlowerById(Integer.toString(flowerType.getFlowerId()));
        if (list.size() == 0) {
            return new ResultEntity(false, "未找到该花卉");
        }
        flowerType.setFlowerId(list.get(0).getFlowerId());
        Example example = new Example(TblFlowerType.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowerId", flowerType.getFlowerId());
        criteria.andEqualTo("typeName", flowerType.getTypeName());
        int result = flowerTypeMapper.delete(flowerType);
        return new ResultEntity(true, "删除花类成功", result);
    }


}
