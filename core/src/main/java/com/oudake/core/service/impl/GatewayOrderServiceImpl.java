package com.oudake.core.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.common.utils.DateUtil;
import com.oudake.core.dao.TblGatewayOrderMapper;
import com.oudake.core.dao.TblUserInfoMapper;
import com.oudake.core.model.TblFlower;
import com.oudake.core.model.TblGatewayOrder;
import com.oudake.core.model.TblUserInfo;
import com.oudake.core.service.FlowerService;
import com.oudake.core.service.GatewayOrderService;
import com.oudake.core.service.SysCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private TblUserInfoMapper userInfoMapper;

    @Autowired
    private FlowerService flowerService;
    @Autowired
    private SysCodeService sysCodeService;

    @Override
    public Layui findOrderByPage(String startTime, String endTime, String txnType, Integer userId, Page page) {
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        Example example = new Example(TblGatewayOrder.class);
        Example.Criteria criteria = example.createCriteria();

        // 用户id
        criteria.andEqualTo("userId", userId);
        // 判断交易类型
        if (txnType != null && !"".equals(txnType)) {
            criteria.andEqualTo("txnType", txnType);
        }
        // 判断时间
        if (startTime != null && endTime != null && !"".equals(startTime) && !"".equals(endTime)) {
            criteria.andBetween("gatewaySysTime", DateUtil.changeDateToOracle(startTime), DateUtil.changeDateToOracle(endTime));
        }

        List<TblGatewayOrder> list = gatewayOrderMapper.selectByExample(example);
        PageInfo<TblGatewayOrder> pageInfo = new PageInfo<>(list);
        Layui layui = new Layui();
        return layui.data((int)pageInfo.getTotal(), list, "orderList");
    }

    @Override
    public synchronized ResultEntity addOrder(Integer flowerId, Integer amount, Integer userId) {
        TblFlower flower = flowerService.findByFlowerId(flowerId);
        if (flower == null) {
            return new ResultEntity(false, "无此花卉");
        }
        // 判断是非售空
        if (flower.getStock() == 0) {
            return new ResultEntity(false, "花卉已售空");
        }
        TblGatewayOrder gatewayOrder = new TblGatewayOrder();
        setDefaultOrdInfo(gatewayOrder);
        // 用户id
        gatewayOrder.setUserId(userId);
        // 交易金额 = 数目 * 单价 * 折扣
        Float discounted = flower.getPrice() * flower.getDiscount() * amount;
        BigDecimal finalPrice = BigDecimal.valueOf(discounted).setScale(2, BigDecimal.ROUND_HALF_UP);
        gatewayOrder.setTxnAmt(finalPrice);
        // 订单具体内容
        String txnRemark = flower.getFlowerName() + "    " + amount + "份<br>";
        gatewayOrder.setTxnRemark(txnRemark);
        try {
            gatewayOrderMapper.insert(gatewayOrder);
        } catch (Exception e) {
            LOGGER.error(">>GatewayOrderServiceImpl, 订单入库失败", e);
            return new ResultEntity(false, "订单入库失败");
        }
        return new ResultEntity(true, "已成功下单", gatewayOrder.getTxnRemark());
    }

    @Override
    public synchronized ResultEntity addOrders(List<TblFlower> flowerList, Integer userId) {
        List<TblFlower> list = new ArrayList<>();
        for (TblFlower flower : flowerList) {
            TblFlower temp = flowerService.findByFlowerId(flower.getFlowerId());
            if (temp == null) {
                return new ResultEntity(false, "存在已错误花卉,请检查订单");
            }
            // 判断是非售空
            if (temp.getStock() == 0) {
                return new ResultEntity(false, "花卉已售空");
            }
            temp.setAmount(flower.getAmount());
            list.add(temp);
        }

        TblGatewayOrder gatewayOrder = new TblGatewayOrder();
        setDefaultOrdInfo(gatewayOrder);
        // 用户id
        gatewayOrder.setUserId(userId);
        // 交易金额 = 数目 * 单价 * 折扣, stringBuilder为订单详细内容
        BigDecimal finalPrice = BigDecimal.ZERO;
        StringBuilder stringBuilder = new StringBuilder();
        for (TblFlower flower : list) {
            Float discounted = flower.getPrice() * flower.getDiscount() * flower.getAmount();
            finalPrice = BigDecimal.valueOf(discounted).setScale(2, BigDecimal.ROUND_HALF_UP).add(finalPrice);
            stringBuilder.append(flower.getFlowerName()).append("    ").append(flower.getAmount()).append("份<br>");
        }
        gatewayOrder.setTxnAmt(finalPrice);
        // 订单具体内容
        String txnRemark =stringBuilder.toString();
        gatewayOrder.setTxnRemark(txnRemark);

        try {
            gatewayOrderMapper.insert(gatewayOrder);
        } catch (Exception e) {
            LOGGER.error(">>GatewayOrderServiceImpl, 订单入库失败", e);
            return new ResultEntity(false, "下单失败");
        }
        return new ResultEntity(true, "已成功下单", gatewayOrder.getTxnRemark());
    }

    @Override
    public ResultEntity pay(Integer gatewayOrdId, Integer userId, String payPwd) {
        TblGatewayOrder gatewayOrder = gatewayOrderMapper.findById(gatewayOrdId);
        if (gatewayOrder == null) {
            return new ResultEntity(false, "该订单不存在");
        }

        TblUserInfo userInfo = userInfoMapper.findByUserId(userId);
        if (userInfo == null) {
            return new ResultEntity(false, "请先完善用户信息");
        }
        if (userInfo.getPayPwd() == null || "".equals(userInfo.getPayPwd())) {
            return new ResultEntity(false, "请先设置支付密码");
        }
        if (!payPwd.equals(userInfo.getPayPwd())) {
            return new ResultEntity(false, "支付密码错误");
        }

        String txnType = sysCodeService.findSysCodeByTypeAndName(Constants.TxnType.NAME, Constants.TxnType.PAYED).getDisplay1();

        if (gatewayOrder.getTxnType().equals(txnType)) {
            return new ResultEntity(false, "请勿重复支付");
        }

        // 订单更改为已支付
        gatewayOrder.setTxnType(txnType);

        Example example = new Example(TblGatewayOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gatewayOrdId", gatewayOrdId);
        gatewayOrderMapper.updateByExampleSelective(gatewayOrder, example);

        return new ResultEntity(true, "已支付");
    }

    private void setDefaultOrdInfo(TblGatewayOrder gatewayOrder) {
        // 币种
        String currency = sysCodeService.findSysCodeByTypeAndName(Constants.Currency.NAME, Constants.Currency.CNY).getDisplay1();
        gatewayOrder.setCurrency(currency);
        // 时间
        gatewayOrder.setGatewaySysTime(DateUtil.getNowTime());
        // 状态(待处理)
        String status = sysCodeService.findSysCodeByTypeAndName(Constants.TxnStatus.NAME, Constants.TxnStatus.WAITING).getDisplay1();
        gatewayOrder.setStatus(status);
        // 交易类型
        String txnType = sysCodeService.findSysCodeByTypeAndName(Constants.TxnType.NAME, Constants.TxnType.WAIT_PAY).getDisplay1();
        gatewayOrder.setTxnType(txnType);
    }
}
