package com.oudake.web.service.impl;

import com.oudake.common.ResultEntity;
import com.oudake.web.model.TblFlower;
import com.oudake.web.service.FavoriteService;
import com.oudake.web.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @author wangyi
 */
@Service
public class FavoriteServiceImpl implements FavoriteService {

    @Resource
    private RedisTemplate<String, TblFlower> redisTemplate;

    @Autowired
    private FlowerService flowerService;

    /**
     * 添加花卉到收藏夹
     * @param userId userId
     * @param flowerId flowerId
     * @return resultEntity
     */
    @Override
    public ResultEntity addFlower(Integer userId, Integer flowerId) {
        TblFlower flower = flowerService.findByFlowerId(flowerId);
        if (flower == null) {
            return new ResultEntity(false, "该花卉不存在");
        }
        redisTemplate.opsForZSet().add(getFavKey(userId), flower, flowerId);
        return new ResultEntity(true, "添加收藏夹成功");
    }

    /**
     * 查找收藏夹, 返回list
     * @param userId userId
     * @return resultEntity
     */
    @Override
    public ResultEntity findFavList(Integer userId) {
        Set<TblFlower> set = redisTemplate.opsForZSet().range(getFavKey(userId), 0, -1);
        if (set.size() == 0) {
            return new ResultEntity(false, "收藏夹为空");
        }
        List<TblFlower> flowerList = new ArrayList<>(set);
        return new ResultEntity(true, "打开收藏夹成功", flowerList);
    }

    /**
     * 收藏夹中移除花卉
     * @param userId userId
     * @param flowerId flowerId
     * @return resultEntity
     */
    @Override
    public ResultEntity removeFlower(Integer userId, Integer flowerId) {
        TblFlower flower = flowerService.findByFlowerId(flowerId);
        if (flower == null) {
            return new ResultEntity(false, "该花卉不存在");
        }
        redisTemplate.opsForZSet().remove(getFavKey(userId), flower);
        return new ResultEntity(true, "删除成功");
    }

    /**
     * 获取收藏夹的key名
     * @param userId userId
     * @return String
     */
    private String getFavKey(Integer userId) {
        return "favorite:" + userId.toString();
    }

}
