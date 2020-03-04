package com.oudake.console.controller;

import com.oudake.common.constants.Constants;
import com.oudake.console.model.TblUserInfo;
import com.oudake.console.service.FlowerStatusService;
import com.oudake.console.service.GatewayOrderService;
import com.oudake.console.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.List;

/**
 * @author wangyi
 */
@Controller
public class IndexController {

    @Autowired
    private UserService userService;
    @Autowired
    private GatewayOrderService gatewayOrderService;
    @Autowired
    private FlowerStatusService flowerStatusService;

    @RequestMapping(value = {"", "index"})
    public String index() {
        return "index";
    }

    @RequestMapping("home/console")
    public ModelAndView console(ModelAndView modelAndView) {
        List<TblUserInfo> activeUserInfo = userService.getActiveUser();
        modelAndView.addObject("activeUserInfo", activeUserInfo);

        // 商品访问量相关
        int allViewCount = flowerStatusService.getAllViewCount(Constants.FlowerStatus.VIEW_COUNT);
        modelAndView.addObject("allViewCount", allViewCount);

        // 订单相关数据
        float monthOrderAmount = gatewayOrderService.getMonthOrderAmount();
        float lastMonthOrderAmount = gatewayOrderService.getLastMonthOrderAmount();
        float allOrderAmount = gatewayOrderService.getAllOrderAmount();
        // 订单数量上涨比率%
        float orderPlusRate = (monthOrderAmount - lastMonthOrderAmount) / monthOrderAmount * 100;

        // 不保留小数
        DecimalFormat decimalFormat = new DecimalFormat("###");
        // 保留2位小数
        DecimalFormat decimalFormat2 = new DecimalFormat("###.00");

        modelAndView.addObject("monthOrderAmount", decimalFormat.format(monthOrderAmount));
        modelAndView.addObject("allOrderAmount", decimalFormat.format(allOrderAmount));
        modelAndView.addObject("orderPlusRate", decimalFormat.format(orderPlusRate));

        // 收入相关数据
        BigDecimal monthMoney = gatewayOrderService.getMonthMoney();
        BigDecimal lastMonthMoney = gatewayOrderService.getLastMonthMoney();
        BigDecimal allMoney = gatewayOrderService.getAllMoney();
        BigDecimal d = monthMoney.subtract(lastMonthMoney);
        if (monthMoney.floatValue() == 0L) {
            monthMoney = BigDecimal.ONE;
        }
        d = d.divide(monthMoney, 2, BigDecimal.ROUND_HALF_EVEN);
        float moneyPlusRate = d.floatValue() * 100;
        modelAndView.addObject("monthMoney", decimalFormat2.format(monthMoney));
        modelAndView.addObject("allMoney", decimalFormat2.format(allMoney));
        modelAndView.addObject("moneyPlusRate", decimalFormat.format(moneyPlusRate));

        // 活跃用户相关数据
        float monthUserAmount = userService.getMonthActiveUserAmount();
        float lastMonthUserAmount = userService.getLastMonthActiveUserAmount();
        float userPlusRate = (monthUserAmount - lastMonthUserAmount) / monthUserAmount * 100;
        modelAndView.addObject("monthUserAmount", decimalFormat.format(monthUserAmount));
        modelAndView.addObject("lastMonthUserAmount", decimalFormat.format(lastMonthUserAmount));
        modelAndView.addObject("userPlusRate", decimalFormat.format(userPlusRate));

        modelAndView.setViewName("home/console");
        return modelAndView;
    }
}
