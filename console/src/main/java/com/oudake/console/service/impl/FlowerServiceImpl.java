package com.oudake.console.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.dao.TblFlowerMapper;
import com.oudake.console.model.TblFlower;
import com.oudake.console.service.FlowerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Author wangyi
 * @Description FlowerServiceImpl
 * @Date 2019/1/15 11:04
 * @Version 1.0
 */
@Service
public class FlowerServiceImpl implements FlowerService {

    private final static Logger LOGGER = LoggerFactory.getLogger(FlowerServiceImpl.class);

    @Autowired
    private TblFlowerMapper flowerMapper;

    /**
     * 根据花名,种类名查询花卉
     * @param flower flower
     * @param page page
     * @return layui
     */
    @Override
    public Layui findFlowerByPage(TblFlower flower, Page page) {
        PageHelper.startPage(page.getCurrPage(), page.getPageSize());
        List<TblFlower> list = flowerMapper.findByFlowerNameAndTypeName(flower);
        PageInfo<TblFlower> pageInfo = new PageInfo<>(list);
        Layui layui = new Layui();
        return layui.data((int)pageInfo.getTotal(), list, "flowerList");
    }

    @Override
    public List<TblFlower> findFlowerById(String flowerId) {
        return flowerMapper.findById(flowerId);
    }

    /**
     * 不允许模糊查询
     * @param flowerName flowerName
     * @return list
     */
    @Override
    public ResultEntity findFlowerIdByName(String flowerName) {
        Example example = new Example(TblFlower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowerName", flowerName);
        List<TblFlower> flowerList = flowerMapper.selectByExample(example);
        if (flowerList.size() == 0) {
            return new ResultEntity(false, "未查询到花卉id");
        }
        return new ResultEntity(true, "已查询到花卉id", flowerList.get(0).getFlowerId());
    }

    @Override
    public ResultEntity addFlower(TblFlower flower) {
        setDefaultInfo(flower);

        if (findFlowerIdByName(flower.getFlowerName()).isSuccess()) {
            return new ResultEntity(false, "该花卉已存在");
        }

        int result = 0;
        try {
            result = flowerMapper.insertSelective(flower);
        } catch (Exception e) {
            LOGGER.error(">>FlowerServiceImpl, 添加花卉失败", e);
            return new ResultEntity(false, "添加花卉失败, 请检查参数", result);
        }
        return new ResultEntity(true, "添加花卉成功", result);
    }

    @Override
    public ResultEntity editFlower(TblFlower flower) {
        Example example = new Example(TblFlower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowerId", flower.getFlowerId());

        int result = 0;

        try {
            result = flowerMapper.updateByExampleSelective(flower, example);
        } catch (Exception e) {
            LOGGER.error(">>FlowerServiceImpl, 修改花卉失败", e);
            return new ResultEntity(false, "修改花卉失败, 请检查参数", result);
        }
        return new ResultEntity(true, "修改花卉成功", result);
    }

    @Override
    public ResultEntity delFlower(Integer flowerId) {
        Example example = new Example(TblFlower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowerId", flowerId);
        int result = flowerMapper.deleteByExample(example);
        if (result > 0) {
            return new ResultEntity(true, "删除花卉成功", result);
        } else {
            return new ResultEntity(false, "删除花卉失败");
        }
    }

    /**
     * @Author wangyi
     * @Description // 设置默认花卉信息
     * @Date 2019/1/18
     * @Param [flower]
     * @return void
    **/
    private void setDefaultInfo(TblFlower flower) {
        flower.setAddition("免费附送精美贺卡，代写您的祝福。(您下单时可填写留言栏)");
        flower.setSendWay("本地区各市县、乡镇、街道（市区内免费配送）");
        flower.setDescription("由于鲜花包扎各地的花艺师不同，可能在花束的形式和搭配上与图片不完全一致，但我们保证鲜花的主花材品种、新鲜程度、数量、颜色与说明一致，谢谢。");
    }

}
