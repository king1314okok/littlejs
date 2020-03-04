package com.oudake.web.service;

import com.oudake.web.model.TblFlowerCode;

import java.util.List;

/**
 * @author wangyi
 */
public interface FlowerCodeService {
    List<TblFlowerCode> findCodeByNames(TblFlowerCode flowerCode);

    List<TblFlowerCode> findSearchCodes();

    TblFlowerCode findCodeById(String typeId);

    boolean isCodeExist(String typeName);
}
