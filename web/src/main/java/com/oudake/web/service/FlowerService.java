package com.oudake.web.service;

import com.oudake.web.model.TblFlower;

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

    void addViewCount(Integer flowerId);

    void addSaleCount(Integer flowerId);

    List<TblFlower> sortByViewCount(List<TblFlower> flowerList);

    List<TblFlower> sortBySaleCount(List<TblFlower> flowerList);

    List<TblFlower> sortByPrice(List<TblFlower> flowerList);

    List<TblFlower> findByKeyword(String keyword);

    List<TblFlower> findIndexFlower();
}
