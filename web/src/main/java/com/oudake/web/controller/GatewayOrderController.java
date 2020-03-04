package com.oudake.web.controller;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.web.model.TblFlower;
import com.oudake.web.service.GatewayOrderService;
import com.oudake.web.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyi
 */
@RestController
@RequestMapping("gatewayOrder")
public class GatewayOrderController extends BaseController {

    /**
     * 该list为flower类型
     */
    private final String CART_SESSION = "cart";

    @Autowired
    private GatewayOrderService gatewayOrderService;

    @GetMapping("")
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("user/gatewayOrder");
        return modelAndView;
    }

    @GetMapping("payPwdForm")
    public ModelAndView payPwdForm(Integer gatewayOrdId, ModelAndView modelAndView) {
        modelAndView.setViewName("payPwdForm");

        if (gatewayOrdId == null || gatewayOrdId <= 0) {
            modelAndView.addObject("errorMsg", "订单号为空或非法");
            return modelAndView;
        }

        modelAndView.addObject("gatewayOrdId", gatewayOrdId);
        modelAndView.addObject("url", "/gatewayOrder/pay");
        return modelAndView;
    }

    @PostMapping("dataGrid")
    public Layui dataGrid(@Valid Page page, BindingResult bindingResult,
                          String startTime, String endTime, String txnType, HttpServletRequest request) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return new Layui(validResult.getMsg());
        }

        Integer userId = getCurrentUser(request).getUserId();
        return gatewayOrderService.findOrderByPage(startTime, endTime, txnType, userId, page);
    }

    @PostMapping("placeOneOrder")
    public ResultEntity placeOneOrder(Integer flowerId, Integer amount, HttpServletRequest request) {
        ResultEntity isLoginResult = isLogin(request);
        if (!isLoginResult.isSuccess()) {
            return isLoginResult;
        }
        if (flowerId == null) {
            return new ResultEntity(false, "flowerId不能为空");
        }
        if (amount == null || amount <= 0) {
            return new ResultEntity(false, "数量不能为空或不合法");
        }
        return gatewayOrderService.addOrder(flowerId, amount, getCurrentUser(request).getUserId());
    }

    @PostMapping("placeOrders")
    public ResultEntity placeOrders(HttpServletRequest request) {
        ResultEntity isLoginResult = isLogin(request);
        if (!isLoginResult.isSuccess()) {
            return isLoginResult;
        }
        List<TblFlower> flowerList = (List<TblFlower>) request.getSession().getAttribute(CART_SESSION);
        if (flowerList == null || flowerList.size() == 0) {
            return new ResultEntity(false, "购物车内无商品");
        }
        // session内的list为完整flower信息, 发送到核心时需要去掉多余字段, 只保留flowerId和amount
        TblFlower flower;
        List<TblFlower> sendList = new ArrayList<>();
        for (TblFlower temp : flowerList) {
            flower = new TblFlower();
            flower.setFlowerId(temp.getFlowerId());
            flower.setAmount(temp.getAmount());
            sendList.add(flower);
        }
        ResultEntity result = gatewayOrderService.addOrders(sendList, getCurrentUser(request).getUserId());
        if (result.isSuccess()) {
            request.getSession().removeAttribute("cart");
            return result;
        }
        return result;
    }

    @PostMapping("pay")
    public ResultEntity pay(Integer gatewayOrdId, String payPwd ,HttpServletRequest request) {
        ResultEntity isLoginResult = isLogin(request);
        if (!isLoginResult.isSuccess()) {
            return isLoginResult;
        }

        if (gatewayOrdId == null || gatewayOrdId <= 0) {
            return new ResultEntity(false, "订单号不能为空或非法");
        }
        if (payPwd == null || "".equals(payPwd)) {
            return new ResultEntity(false, "请输入支付密码");
        }
        return gatewayOrderService.pay(gatewayOrdId, getCurrentUser(request).getUserId(), payPwd);
    }

}
