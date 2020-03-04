package com.oudake.console.controller;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblGatewayOrder;
import com.oudake.console.service.GatewayOrderService;
import com.oudake.console.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author wangyi
 * @Description GatewayOrderController
 * @Date 2019/1/24 20:20
 * @Version 1.0
 */
@RestController
@RequestMapping("gatewayOrder")
public class GatewayOrderController {

    @Autowired
    private GatewayOrderService gatewayOrderService;

    @GetMapping()
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("gatewayOrder/gatewayOrder");
        return modelAndView;
    }

    @GetMapping("processForm")
    public ModelAndView processForm(Integer gatewayOrdId, ModelAndView modelAndView) {
        if (gatewayOrdId == null || gatewayOrdId <= 0) {
            ResultUtil.errorModel("订单号为空或非法", modelAndView);
        }
        TblGatewayOrder gatewayOrder = gatewayOrderService.findByGatewayOrdId(gatewayOrdId);
        if (gatewayOrder == null) {
            ResultUtil.errorModel("该订单不存在", modelAndView);
        }
        modelAndView.addObject("order", gatewayOrder);
        modelAndView.setViewName("gatewayOrder/processForm");
        return modelAndView;
    }

    @PostMapping("dataGrid")
    public Layui dataGrid(@Valid Page page, BindingResult bindingResult,
                          String startTime, String endTime, String txnType) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return Layui.error(validResult.getMsg());
        }
        return gatewayOrderService.findOrderByPage(startTime, endTime, txnType, page);
    }

    @PostMapping("processOrder")
    public ResultEntity processOrder(Integer gatewayOrdId, String txnStatus) {
        if (gatewayOrdId == null || gatewayOrdId <= 0) {
            return new ResultEntity(false,"订单号为空或非法");
        }
        if (StringUtils.isBlank(txnStatus)) {
            return new ResultEntity(false,"txnStatus为空");
        }
        return gatewayOrderService.acceptOrder(gatewayOrdId, txnStatus);
    }

    @PostMapping("getWeekSale")
    public ResultEntity getWeekSale() {
        List<Integer> weekSale = gatewayOrderService.getWeekSale();
        return new ResultEntity(true, "获取周交易额成功", weekSale);
    }
}
