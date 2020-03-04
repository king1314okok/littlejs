package com.oudake.web;

import com.oudake.common.ResultEntity;
import com.oudake.web.service.GatewayOrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GatewayOrderTest {

    @Autowired
    private GatewayOrderService gatewayOrderService;

    @Test
    public void orderTest() {
        ResultEntity result = gatewayOrderService.pay(3, 1, "111111");
        System.out.println(result.getMsg());
    }
}
