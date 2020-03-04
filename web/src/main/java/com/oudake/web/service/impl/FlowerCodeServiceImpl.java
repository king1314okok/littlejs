package com.oudake.web.service.impl;

import com.oudake.web.dao.TblFlowerCodeMapper;
import com.oudake.web.model.TblFlower;
import com.oudake.web.model.TblFlowerCode;
import com.oudake.web.service.FlowerCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author wangyi
 */
@Service
public class FlowerCodeServiceImpl implements FlowerCodeService {

    @Autowired
    private TblFlowerCodeMapper flowerCodeMapper;

    /**
     * 通过种类名(可选),父类名(可选)来查找code
     * @param flowerCode flowerCode
     * @return list
     */
    @Override
    public List<TblFlowerCode> findCodeByNames(TblFlowerCode flowerCode) {
        Example example = new Example(TblFlowerCode.class);
        Example.Criteria criteria = example.createCriteria();

        if (flowerCode.getTypeName() != null && !"".equals(flowerCode.getTypeName())) {
            criteria.andLike("typeName", "%" + flowerCode.getTypeName() + "%");
        }
        if (flowerCode.getFatherName() != null && !"".equals(flowerCode.getFatherName())) {
            criteria.andLike("fatherName", "%" + flowerCode.getFatherName() + "%");
        }

        example.setOrderByClause("TYPE_ID asc");
        return flowerCodeMapper.selectByExample(example);
    }

    @Override
    public List<TblFlowerCode> findSearchCodes() {
        return flowerCodeMapper.findSearchCodes();
    }

    /**
     * 通过id查找code
     * @param typeId typeId
     * @return TblFlowerCode
     */
    @Override
    public TblFlowerCode findCodeById(String typeId) {
        Example example = new Example(TblFlowerCode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("typeId", Integer.valueOf(typeId));
        return flowerCodeMapper.selectOneByExample(example);
    }

    /**
     * 判断code是否存在
     * @param typeName 种类名
     * @return boolean
     */
    @Override
    public boolean isCodeExist(String typeName) {
        Example example = new Example(TblFlowerCode.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("typeName", typeName);
        return flowerCodeMapper.selectByExample(example).size() != 0;
    }
}
