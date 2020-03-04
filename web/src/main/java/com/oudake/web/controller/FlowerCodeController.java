package com.oudake.web.controller;

import com.oudake.common.ResultEntity;
import com.oudake.web.model.TblFlowerCode;
import com.oudake.web.service.FlowerCodeService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wangyi
 */
@RestController
@RequestMapping("flowerCode")
public class FlowerCodeController {

    @Autowired
    private FlowerCodeService flowerCodeService;

    @PostMapping("findCodeByFatherName")
    public ResultEntity findCodeByFatherName(String fatherName, HttpServletRequest request) {
        if (StringUtils.isBlank(fatherName)) {
            return new ResultEntity(false, "fatherName为空");
        }
        TblFlowerCode flowerCode = new TblFlowerCode();
        flowerCode.setFatherName(fatherName);
        List<TblFlowerCode> codeList = flowerCodeService.findCodeByNames(flowerCode);
        return new ResultEntity(true, "查找成功, 共" + codeList.size() + " 条", codeList);
    }
}
