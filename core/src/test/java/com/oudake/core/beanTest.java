package com.oudake.core;

import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.core.dao.TblGatewayOrderMapper;
import com.oudake.core.init.TxnType;
import com.oudake.core.model.TblGatewayOrder;
import com.oudake.core.service.FlowerService;
import com.oudake.core.service.GatewayOrderService;
import com.oudake.core.service.SysCodeService;
import com.oudake.core.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class beanTest {

    @Autowired
    private GatewayOrderService gatewayOrderService;

    @Test
    public void beanTest() {
        ResultEntity resultEntity = gatewayOrderService.addOrder(1, 3, 1);
        System.out.println(resultEntity.getMsg());
    }
}
