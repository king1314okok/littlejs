package com.oudake.web.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.common.msg.TxnMessage;
import com.oudake.common.utils.DateUtil;
import com.oudake.web.dao.TblGatewayOrderMapper;
import com.oudake.web.model.TblFlower;
import com.oudake.web.model.TblGatewayOrder;
import com.oudake.web.mq.CoreSender;
import com.oudake.web.service.FlowerService;
import com.oudake.web.service.GatewayOrderService;
import com.oudake.web.service.SysCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author wangyi
 */
@Service
public class GatewayOrderServiceImpl implements GatewayOrderService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GatewayOrderServiceImpl.class);

    @Autowired
    private TblGatewayOrderMapper gatewayOrderMapper;
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private SysCodeService sysCodeService;
    @Autowired
    private CoreSender coreSender;

    @Override
    public Layui findOrderByPage(String startTime, String endTime, String txnType, Integer userId, Page page) {
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        startTime = DateUtil.changeDateToOracle(startTime);
        endTime = DateUtil.changeDateToOracle(endTime);
        List<TblGatewayOrder> list = gatewayOrderMapper.findByPage(startTime, endTime, txnType, userId);
        PageInfo<TblGatewayOrder> pageInfo = new PageInfo<>(list);
        Layui layui = new Layui();
        return layui.data((int)pageInfo.getTotal(), list, "orderList");
    }

    @Override
    public ResultEntity addOrder(Integer flowerId, Integer amount, Integer userId) {
        if (!isFlowerNormal(flowerId)) {
            return new ResultEntity(false, "该商品已下架");
        }
        TxnMessage txnMessage = new TxnMessage();
        // 交易类型
        txnMessage.setTxnType(Constants.TxnMessage.PLACE_ONE_ORDER);
        // 应答状态
        txnMessage.setSuccess(false);
        // 用户id
        txnMessage.setUserId(userId);
        // 交易id
        txnMessage.setTxnId(flowerId);
        // 交易数量
        txnMessage.setAmount(amount);
        TxnMessage response = coreSender.sendAndReceive(txnMessage);
        // 下单记录入库
        flowerService.addSaleCount(flowerId);
        return new ResultEntity(response.isSuccess(), response.getMsg());
    }

    @Override
    public ResultEntity addOrders(List<TblFlower> flowerList, Integer userId) {
        for (TblFlower flower : flowerList) {
            if (!isFlowerNormal(flower.getFlowerId())) {
                return new ResultEntity(false, "商品:" + flower.getFlowerName() + "已下架");
            }
        }
        TxnMessage txnMessage = new TxnMessage();
        // 交易类型
        txnMessage.setTxnType(Constants.TxnMessage.PLACE_ORDERS);
        // 应答状态
        txnMessage.setSuccess(false);
        // 用户id
        txnMessage.setUserId(userId);
        // 数据(花卉list)
        txnMessage.setObj(flowerList);
        TxnMessage response = coreSender.sendAndReceive(txnMessage);
        for (TblFlower flower : flowerList) {
            // 下单记录入库
            flowerService.addSaleCount(flower.getFlowerId());
        }
        return new ResultEntity(response.isSuccess(), response.getMsg());
    }

    @Override
    public ResultEntity pay(Integer gatewayOrdId, Integer userId, String payPwd) {
        TxnMessage txnMessage = new TxnMessage();
        // 交易类型
        txnMessage.setTxnType(Constants.TxnMessage.PAY);
        // 应答状态
        txnMessage.setSuccess(false);
        // 用户id
        txnMessage.setUserId(userId);
        // 交易id
        txnMessage.setTxnId(gatewayOrdId);
        // 交易密码
        txnMessage.setTxnRemark(payPwd);

        TxnMessage response = coreSender.sendAndReceive(txnMessage);
        return new ResultEntity(response.isSuccess(), response.getMsg());
    }

    private boolean isFlowerNormal(Integer flowerId) {
        TblFlower flower = flowerService.findByFlowerId(flowerId);
        String normalCode = sysCodeService.findSysCodeByTypeAndName(Constants.FlowerStatus.NAME, Constants.FlowerStatus.NORMAL).getDisplay1();
        return flower.getFlowerStatus().equals(normalCode);
    }
}
