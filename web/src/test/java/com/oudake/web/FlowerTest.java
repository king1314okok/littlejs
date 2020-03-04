package com.oudake.web;

import com.oudake.web.model.TblFlower;
import com.oudake.web.service.FavoriteService;
import com.oudake.web.service.FlowerCodeService;
import com.oudake.web.service.FlowerService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author wangyi
 * @Description flowerTest
 * @Date 2019/1/21 18:39
 * @Version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FlowerTest {

    private static final String VIEW_COUNT_NAME = "viewCount";
    private String[] countArray = new String[10000];

    @Resource
    private RedisTemplate<String, String> redisTemplate;
    @Autowired
    private FlowerService flowerService;
    @Autowired
    private FlowerCodeService flowerCodeService;
    @Autowired
    private FavoriteService favoriteService;

    @Test
    public void typeListTest() {
        String keyword = "爱情鲜花";
        List<TblFlower> flowerList = flowerService.findByKeyword(keyword);
        List<TblFlower> result = flowerService.sortByPrice(flowerList);
        System.out.println("--");
    }

}
