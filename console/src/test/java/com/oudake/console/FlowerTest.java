package com.oudake.console;

import com.oudake.common.constants.Constants;
import com.oudake.console.model.TblFlowerStatus;
import com.oudake.console.service.FlowerCodeService;
import com.oudake.console.service.FlowerService;
import com.oudake.console.service.FlowerStatusService;
import com.oudake.console.service.LoginService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowerTest {

    @Resource
    private RedisTemplate<String, Integer> redisTemplate;
    @Autowired
    private LoginService loginService;
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private FlowerCodeService flowerCodeService;
    @Autowired
    private FlowerStatusService flowerStatusService;

    @Test
    public void FlowerCodeTest() {
        Integer count = flowerStatusService.getAllViewCount(Constants.FlowerStatus.SALE_COUNT);
        System.out.println(count);
    }


}

