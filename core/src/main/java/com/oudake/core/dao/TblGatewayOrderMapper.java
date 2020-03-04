package com.oudake.core.dao;

import com.oudake.core.model.TblGatewayOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author wangyi
 */
public interface TblGatewayOrderMapper extends Mapper<TblGatewayOrder> {
    int insert(TblGatewayOrder record);

    int insertSelective(TblGatewayOrder record);

    TblGatewayOrder findById(@Param("gatewayOrdId") Integer gatewayOrdId);
}