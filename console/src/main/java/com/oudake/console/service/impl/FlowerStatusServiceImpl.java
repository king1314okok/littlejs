package com.oudake.console.service.impl;

import com.oudake.common.constants.Constants;
import com.oudake.console.dao.TblFlowerStatusMapper;
import com.oudake.console.model.TblFlowerStatus;
import com.oudake.console.service.FlowerStatusService;
import com.oudake.console.service.SysCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangyi
 */
@Service
public class FlowerStatusServiceImpl implements FlowerStatusService {

    @Autowired
    private TblFlowerStatusMapper flowerStatusMapper;
    @Autowired
    private SysCodeService sysCodeService;

    /**
     * 根据id,type查找数目
     * @param flowerId flowerId
     * @param typeName typeName
     * @return TblFlowerStatus
     */
    @Override
    public TblFlowerStatus findByFLowerStatus(Integer flowerId, String typeName) {
        String type = sysCodeService.findSysCodeByTypeAndName(Constants.FlowerStatus.NAME, typeName).getDisplay1();
        return flowerStatusMapper.findById_Type(flowerId, type);
    }

    /**
     * 根据typeName查找全部花卉的浏览次数
     * @param typeName
     * @return
     */
    @Override
    public Integer getAllViewCount(String typeName) {
        String type = sysCodeService.findSysCodeByTypeAndName(Constants.FlowerStatus.NAME, typeName).getDisplay1();
        return flowerStatusMapper.getAllViewCount(type);
    }
}
