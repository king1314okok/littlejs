package com.oudake.core.service;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.core.model.TblFlower;

import java.util.List;

/**
 * @author wangyi
 */
public interface GatewayOrderService {
    Layui findOrderByPage(String startTime, String endTime, String txnType, Integer userId, Page page);

    ResultEntity addOrder(Integer flowerId, Integer amount, Integer userId);

    ResultEntity addOrders(List<TblFlower> flowerList, Integer userId);

    ResultEntity pay(Integer gatewayOrdId, Integer userId, String payPwd);
}
