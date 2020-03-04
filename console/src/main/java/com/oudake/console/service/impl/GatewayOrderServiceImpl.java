package com.oudake.console.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.common.utils.DateUtil;
import com.oudake.console.dao.TblGatewayOrderMapper;
import com.oudake.console.model.TblGatewayOrder;
import com.oudake.console.service.GatewayOrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author wangyi
 * @Description GatewayOrderServiceImpl
 * @Date 2019/1/24 18:17
 * @Version 1.0
 */
@Service
public class GatewayOrderServiceImpl implements GatewayOrderService {

    private final static Logger LOGGER = LoggerFactory.getLogger(GatewayOrderServiceImpl.class);

    @Autowired
    private TblGatewayOrderMapper gatewayOrderMapper;

    /**
     * 请不要使用,控制台不可能添加订单
     * @param gatewayOrder gatewayOrder
     * @return
     */
    @Deprecated
    @Override
    public ResultEntity addOrder(TblGatewayOrder gatewayOrder) {
        int result = gatewayOrderMapper.insertSelective(gatewayOrder);
        return new ResultEntity(true, "添加订单成功", result);
    }

    @Override
    public TblGatewayOrder findByGatewayOrdId(Integer gatewayOrdId) {
        return gatewayOrderMapper.findOrdById(gatewayOrdId);
    }

    @Override
    public Layui findOrderByPage(String startTime, String endTime, String txnType, Page page) {
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());

        startTime = DateUtil.changeDateToOracle(startTime);
        endTime = DateUtil.changeDateToOracle(endTime);
        List<TblGatewayOrder> list = gatewayOrderMapper.findOrdByPage_Time_TxnType(startTime, endTime ,txnType);
        PageInfo<TblGatewayOrder> pageInfo = new PageInfo<>(list);
        Layui layui = new Layui();
        return layui.data((int)pageInfo.getTotal(), list, "orderList");
    }

    @Override
    public ResultEntity acceptOrder(Integer gatewayOrdId, String txnStatus) {
        Example example = new Example(TblGatewayOrder.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("gatewayOrdId", gatewayOrdId);

        TblGatewayOrder gatewayOrder = findByGatewayOrdId(gatewayOrdId);
        gatewayOrder.setStatus(txnStatus);

        gatewayOrderMapper.updateByExampleSelective(gatewayOrder, example);
        return new ResultEntity(true, "已处理");
    }

    @Override
    public BigDecimal getMonthMoney() {
        // 获取当前年月,如201903
        String month = DateUtil.getMonth();
        List<TblGatewayOrder> list = gatewayOrderMapper.findOrdByDate(month);
        BigDecimal money = BigDecimal.ZERO;
        for (TblGatewayOrder gatewayOrder : list) {
            money = gatewayOrder.getTxnAmt().add(money);
        }
        return money;
    }

    @Override
    public BigDecimal getLastMonthMoney() {
        // 获取上一个年月,如201902
        String month = DateUtil.getMonth(-1);
        List<TblGatewayOrder> list = gatewayOrderMapper.findOrdByDate(month);
        BigDecimal money = BigDecimal.ZERO;
        for (TblGatewayOrder gatewayOrder : list) {
            money = gatewayOrder.getTxnAmt().add(money);
        }
        return money;
    }

    @Override
    public BigDecimal getAllMoney() {
        return gatewayOrderMapper.getAllMoney();
    }

    @Override
    public List<Integer> getWeekSale() {
        List<Integer> weekSale = new ArrayList<>();
        for (int i = -6; i <= 0; i++) {
            String date = DateUtil.getBeforeDay(DateUtil.getNowDate(), i);
            Integer amount = gatewayOrderMapper.findOrdByDate(date).size();
            weekSale.add(amount);
        }
        return weekSale;
    }

    @Override
    public Integer getMonthOrderAmount() {
        // 获取当前年月,如201903
        String month = DateUtil.getMonth();
        List<TblGatewayOrder> list = gatewayOrderMapper.findOrdByDate(month);
        return list.size();
    }

    @Override
    public Integer getLastMonthOrderAmount() {
        // 获取上一个年月,如201902
        String month = DateUtil.getMonth(-1);
        List<TblGatewayOrder> list = gatewayOrderMapper.findOrdByDate(month);
        return list.size();
    }

    @Override
    public Integer getAllOrderAmount() {
        return gatewayOrderMapper.getAllOrderAmount();
    }
}
