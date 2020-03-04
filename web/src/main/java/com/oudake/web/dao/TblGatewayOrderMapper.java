package com.oudake.web.dao;

import com.oudake.web.model.TblGatewayOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

/**
 * @author wangyi
 */
public interface TblGatewayOrderMapper extends Mapper<TblGatewayOrder> {
    int insert(TblGatewayOrder record);

    int insertSelective(TblGatewayOrder record);

    TblGatewayOrder findById(@Param("gatewayOrdId") Integer gatewayOrdId);

    List<TblGatewayOrder> findByPage(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                     @Param("txnType") String txnType, @Param("userId") Integer userId);
}