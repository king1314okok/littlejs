package com.oudake.console.controller;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.console.model.TblFlowerType;
import com.oudake.console.service.FlowerTypeService;
import com.oudake.console.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;

/**
 * @author wangyi
 */
@Controller
@RequestMapping("flowerType")
public class FlowerTypeController {

    @Autowired
    private FlowerTypeService flowerTypeService;

    @RequestMapping()
    public String index() {
        return "flowerType/flowerType";
    }

    @RequestMapping("form")
    public String form() {
        return "flowerType/flowerTypeForm";
    }

    @RequestMapping("dataGrid")
    @ResponseBody
    public Layui dataGrid(@Valid Page page, BindingResult bindingResult, TblFlowerType flowerType) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return Layui.error(validResult.getMsg());
        }
        return flowerTypeService.findTypeByPage(flowerType, page);
    }

    @RequestMapping("add")
    @ResponseBody
    public ResultEntity add(@Valid TblFlowerType flowerType, BindingResult bindingResult) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        return flowerTypeService.addFlowerType(flowerType);
    }

    @RequestMapping("del")
    @ResponseBody
    public ResultEntity del(TblFlowerType flowerType) {
        if (flowerType.getFlowerId() == null) {
            return new ResultEntity(false, "花卉id不能为空");
        }
        if (flowerType.getTypeName() == null || "".equals(flowerType.getTypeName())) {
            return new ResultEntity(false, "种类名不能为空");
        }
        return flowerTypeService.delFlowerType(flowerType);
    }
}
