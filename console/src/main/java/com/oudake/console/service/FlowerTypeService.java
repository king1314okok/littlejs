package com.oudake.console.service;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblFlowerType;

/**
 * @author wangyi
 */
public interface FlowerTypeService {
    Layui findTypeByPage(TblFlowerType flowerType, Page page);

    ResultEntity addFlowerType(TblFlowerType flowerType);

    ResultEntity delFlowerType(TblFlowerType flowerType);
}
