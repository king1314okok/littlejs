package com.oudake.console;

import com.oudake.common.constants.Constants;
import com.oudake.common.utils.DateUtil;
import com.oudake.console.model.TblGatewayOrder;
import com.oudake.console.service.GatewayOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @Author wangyi
 * @Description GatewayOrderTest
 * @Date 2019/1/24 19:50
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayOrderTest {

    @Autowired
    private GatewayOrderService gatewayOrderService;

    @Test
    public void orderTest() {
        List<Integer> num = gatewayOrderService.getWeekSale();
        System.out.println("num: " + num.size());
    }

}
