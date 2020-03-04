package com.oudake.core.service;

import com.oudake.core.model.TblFlower;

import java.util.List;

/**
 * @Author wangyi
 * @Description FlowerService
 * @Date 2019/1/21 17:59
 * @Version 1.0
 */
public interface FlowerService {

    TblFlower findByFlowerId(Integer flowerId);

    List<TblFlower> findByTypeName(String flowerName);

    List<TblFlower> findByFlowerName(String flowerName);

    List<TblFlower> findByFlowerNameAndTypeName(TblFlower flower);
}
