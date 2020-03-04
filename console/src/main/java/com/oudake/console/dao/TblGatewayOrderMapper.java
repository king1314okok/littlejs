package com.oudake.console.dao;

import com.oudake.console.model.TblGatewayOrder;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author wangyi
 */
public interface TblGatewayOrderMapper extends Mapper<TblGatewayOrder> {
    int insert(TblGatewayOrder record);

    int insertSelective(TblGatewayOrder record);

    TblGatewayOrder findOrdById(@Param("gatewayOrdId") Integer gatewayOrdId);

    List<TblGatewayOrder> findOrdByPage_Time_TxnType(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                                     @Param("txnType") String txnType);

    List<TblGatewayOrder> findOrdByDate(@Param("date") String date);

    Integer getAllOrderAmount();

    BigDecimal getAllMoney();
}