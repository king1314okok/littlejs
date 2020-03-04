package com.oudake.console.service;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblFlowerCode;

import java.util.List;

/**
 * @Author wangyi
 * @Description FlowerCodeService
 * @Date 2019/1/15 11:44
 * @Version 1.0
 */
public interface FlowerCodeService {

    Layui findCodeByPage(TblFlowerCode flowerCode, Page page);

    TblFlowerCode findCodeById(String typeId);

    boolean isCodeExist(String typeName);

    ResultEntity addCode(TblFlowerCode flowerCode);

    ResultEntity delCode(Integer typeId);

    ResultEntity editCode(TblFlowerCode flowerCode);
}
