package com.oudake.console.service;

import com.oudake.console.model.TblFlowerStatus;

/**
 * @author wangyi
 */
public interface FlowerStatusService {
    TblFlowerStatus findByFLowerStatus(Integer flowerId, String type);

    Integer getAllViewCount(String typeName);
}
