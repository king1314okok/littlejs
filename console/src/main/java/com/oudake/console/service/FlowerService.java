package com.oudake.console.service;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblFlower;

import java.util.List;

/**
 * @Author wangyi
 * @Description FlowerService
 * @Date 2019/1/15 11:04
 * @Version 1.0
 */
public interface FlowerService {
    Layui findFlowerByPage(TblFlower flower, Page page);

    List<TblFlower> findFlowerById(String flowerId);

    ResultEntity findFlowerIdByName(String flowerName);

    ResultEntity addFlower(TblFlower flower);

    ResultEntity editFlower(TblFlower flower);

    ResultEntity delFlower(Integer flowerId);
}
