package com.oudake.console.service;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblGatewayOrder;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author wangyi
 * @Description GatewayOrderService
 * @Date 2019/1/24 18:17
 * @Version 1.0
 */
public interface GatewayOrderService {
    ResultEntity addOrder(TblGatewayOrder gatewayOrder);

    TblGatewayOrder findByGatewayOrdId(Integer gatewayOrdId);

    Layui findOrderByPage(String startTime, String endTime, String txnType, Page page);

    ResultEntity acceptOrder(Integer gatewayOrdId, String txnStatus);

    BigDecimal getMonthMoney();

    BigDecimal getLastMonthMoney();

    BigDecimal getAllMoney();

    List<Integer> getWeekSale();

    Integer getMonthOrderAmount();

    Integer getLastMonthOrderAmount();

    Integer getAllOrderAmount();
}
