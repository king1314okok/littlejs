package com.oudake.web.service.impl;

import com.oudake.common.constants.Constants;
import com.oudake.web.dao.TblFlowerMapper;
import com.oudake.web.dao.TblFlowerStatusMapper;
import com.oudake.web.model.TblFlower;
import com.oudake.web.model.TblFlowerStatus;
import com.oudake.web.service.FlowerService;
import com.oudake.web.service.SysCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Author wangyi
 * @Description FlowerServiceImpl
 * @Date 2019/1/21 18:00
 * @Version 1.0
 */
@Service
public class FlowerServiceImpl implements FlowerService {

    @Autowired
    private TblFlowerMapper flowerMapper;
    @Autowired
    private TblFlowerStatusMapper flowerStatusMapper;
    @Autowired
    private SysCodeService sysCodeService;

    @Override
    public TblFlower findByFlowerId(Integer flowerId) {
        Example example = new Example(TblFlower.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("flowerId", flowerId);
        TblFlower flower = flowerMapper.selectOneByExample(example);
        return flower;
    }

    @Override
    public List<TblFlower> findByTypeName(String typeName) {
        return flowerMapper.findByTypeName(typeName);
    }

    @Override
    public List<TblFlower> findByFlowerName(String flowerName) {
        return flowerMapper.findByFlowerName(flowerName);
    }

    private void addCount(Integer flowerId ,String type) {
        TblFlower flower = findByFlowerId(flowerId);
        if (flower == null) {
            return;
        }
        TblFlowerStatus flowerStatus = flowerStatusMapper.findByIdAndType(flowerId, type);
        if (flowerStatus == null) {
            flowerStatus = new TblFlowerStatus();
            flowerStatus.setFlowerId(flowerId);
            flowerStatus.setType(type);
            flowerStatus.setAmount(1);
            flowerStatusMapper.insertSelective(flowerStatus);
        } else {
            // 次数加1
            flowerStatus.setAmount(flowerStatus.getAmount() + 1);
            Example example = new Example(TblFlowerStatus.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("flowerId", flowerId);
            criteria.andEqualTo("type", type);
            flowerStatusMapper.updateByExampleSelective(flowerStatus, example);
        }
    }

    @Override
    public void addViewCount(Integer flowerId) {
        String type = sysCodeService.findSysCodeByTypeAndName(Constants.FlowerStatus.NAME, Constants.FlowerStatus.VIEW_COUNT).getDisplay1();
        addCount(flowerId, type);
    }

    @Override
    public void addSaleCount(Integer flowerId) {
        String type = sysCodeService.findSysCodeByTypeAndName(Constants.FlowerStatus.NAME, Constants.FlowerStatus.SALE_COUNT).getDisplay1();
        addCount(flowerId, type);
    }

    private void getViewCount(List<TblFlower> flowerList) {
        String type = sysCodeService.findSysCodeByTypeAndName(Constants.FlowerStatus.NAME, Constants.FlowerStatus.VIEW_COUNT).getDisplay1();
        for (TblFlower flower : flowerList) {
            TblFlowerStatus status = flowerStatusMapper.findByIdAndType(flower.getFlowerId(), type);
            if (status != null) {
                flower.setCount(status.getAmount());
            }
        }
    }

    private void getSaleCount(List<TblFlower> flowerList) {
        String type = sysCodeService.findSysCodeByTypeAndName(Constants.FlowerStatus.NAME, Constants.FlowerStatus.SALE_COUNT).getDisplay1();
        for (TblFlower flower : flowerList) {
            TblFlowerStatus status = flowerStatusMapper.findByIdAndType(flower.getFlowerId(), type);
            if (status != null) {
                flower.setCount(status.getAmount());
            }
        }
    }

    @Override
    public List<TblFlower> sortByViewCount(List<TblFlower> flowerList) {
        if (flowerList == null || flowerList.size() == 0) {
            return flowerList;
        }
        getViewCount(flowerList);
        return sortFlower(flowerList);
    }

    @Override
    public List<TblFlower> sortBySaleCount(List<TblFlower> flowerList) {
        if (flowerList == null || flowerList.size() == 0) {
            return flowerList;
        }
        getSaleCount(flowerList);
        return sortFlower(flowerList);
    }

    @Override
    public List<TblFlower> sortByPrice(List<TblFlower> flowerList) {
        // 重新添加数组,不影响原数组
        List<TblFlower> sortList = new ArrayList<>(flowerList);
        int size = sortList.size();
        for (int i = 0; i < size - 1; i++) {
            for (int j = 0; j < size - i -1; j++) {
                float former = sortList.get(j).getPrice() * sortList.get(j).getDiscount();
                float latter = sortList.get(j+1).getPrice() * sortList.get(j+1).getDiscount();
                if (former > latter) {
                    TblFlower temp = sortList.get(j+1);
                    sortList.set(j+1, sortList.get(j));
                    sortList.set(j, temp);
                }
            }
        }
        return sortList;
    }

    private List<TblFlower> sortFlower(List<TblFlower> flowerList) {
        List<TblFlower> sortList = new ArrayList<>();
        sortList.add(flowerList.get(0));
        // 从flowerList的第二个开始比较
        for (int i = 1; i < flowerList.size(); i++) {
            int size = sortList.size();
            TblFlower flower = flowerList.get(i);
            // 塞进sortList,根据count desc排序
            for (int j = 0; j < size; j++) {
                TblFlower temp = sortList.get(j);
                Integer countTemp = temp.getCount();
                Integer countFlower = flower.getCount();

                if (countFlower == null) {
                    sortList.add(size, flower);
                    break;
                } else if (countTemp == null) {
                    sortList.add(0, flower);
                    break;
                } else {
                    if (countFlower >= countTemp) {
                        sortList.add(j, flower);
                        break;
                    }
                    if (j == i - 1) {
                        sortList.add(i, flower);
                        break;
                    }
                }
            }
        }
        return sortList;
    }

    @Override
    public List<TblFlower> findByKeyword(String keyword) {
        List<String> keywordList = new ArrayList<>();
        String flowerName = null;

        String[] temp = keyword.split("\\+");
        // 其中可能存在""
        keywordList.addAll(Arrays.asList(temp));
        // 花名取list第一个
        flowerName = keywordList.get(0);
        List<TblFlower> flowerList = findByFlowerName(flowerName);
        List<TblFlower> repeatList = new ArrayList<>();
        List<TblFlower> tempList = new ArrayList<>();
        Set<TblFlower> set = new HashSet<>();
        // 如果没查到花,把第一个keyword当成typeName
        if (flowerList.size() <= 0) {
            flowerName = null;
            if (keywordList.size() == 1) {
                List<TblFlower> single = flowerMapper.findByTypeName(keywordList.get(0));
                // 只有一个keyword,直接返回其所有结果
                return single;
            }
            // 先把所有条件下的话都添加进tempList
            for (String key : keywordList) {
                List<TblFlower> nowList = flowerMapper.findByTypeName(key);
                tempList.addAll(nowList);
            }
            // 遍历tempList,统计一个花出现的次数,比较是否等于keyword.size
            for (TblFlower flower : tempList) {
                int count = 0;
                for (TblFlower nowFlower : tempList) {
                    if (flower.getFlowerName().equals(nowFlower.getFlowerName())) {
                        count++;
                    }
                }
                if (count == keywordList.size()) {
                    set.add(flower);
                }
                count = 0;
            }
            repeatList.addAll(set);
            return repeatList;
        }
        // 查到花的情况下
        else {
            // 移除第一个花名keyword,剩下的都是typeName
            keywordList.remove(0);
            if (keywordList.size() == 0) {
                return flowerList;
            }
            // 先把所有条件下的话都添加进tempList
            for (String key : keywordList) {
                List<TblFlower> nowList = flowerMapper.findByTypeName(key);
                tempList.addAll(nowList);
            }
            // 遍历tempList,统计一个花出现的次数,比较是否等于keyword.size
            for (TblFlower flower : tempList) {
                int count = 0;
                for (TblFlower nowFlower : tempList) {
                    if (flower.getFlowerName().equals(nowFlower.getFlowerName())) {
                        count++;
                    }
                }
                if (count == keywordList.size()) {
                    set.add(flower);
                }
                count = 0;
            }
            for (TblFlower flower : flowerList) {
                if (set.contains(flower)) {
                    repeatList.add(flower);
                }
            }
            return repeatList;
        }
    }

    @Override
    public List<TblFlower> findIndexFlower() {
        return flowerMapper.findIndexFlower();
    }

}
