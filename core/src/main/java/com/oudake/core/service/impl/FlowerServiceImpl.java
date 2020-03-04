package com.oudake.core.service.impl;

import com.oudake.core.dao.TblFlowerMapper;
import com.oudake.core.model.TblFlower;
import com.oudake.core.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author wangyi
 * @Description FlowerServiceImpl
 * @Date 2019/1/21 18:00
 * @Version 1.0
 */
@Service
public class FlowerServiceImpl implements FlowerService {

    @Autowired
    private TblFlowerMapper flowerMapper;

    @Override
    public TblFlower findByFlowerId(Integer flowerId) {
        Example example = new Example(TblFlower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowerId", flowerId);
        return flowerMapper.selectOneByExample(example);
    }

    @Override
    public List<TblFlower> findByTypeName(String typeName) {
        return flowerMapper.findByTypeName(typeName);
    }

    @Override
    public List<TblFlower> findByFlowerName(String flowerName) {
        return flowerMapper.findByFlowerName(flowerName);
    }

    @Override
    public List<TblFlower> findByFlowerNameAndTypeName(TblFlower flower) {
        return flowerMapper.findByFlowerNameAndTypeName(flower);
    }

}
