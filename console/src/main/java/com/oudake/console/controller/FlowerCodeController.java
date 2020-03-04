package com.oudake.console.controller;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.console.model.TblFlowerCode;
import com.oudake.console.service.FlowerCodeService;
import com.oudake.console.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 * @Author wangyi
 * @Description 花卉资源
 * @Date 2019/1/15 13:37
 * @Version 1.0
 */
@RestController
@RequestMapping("flowerCode")
public class FlowerCodeController {

    @Autowired
    private FlowerCodeService flowerCodeService;

    @GetMapping()
    public ModelAndView index(ModelAndView modelAndView) {
        modelAndView.setViewName("flowerCode/flowerCode");
        return modelAndView;
    }

    @GetMapping("form")
    public ModelAndView form(String type, String typeId, ModelAndView modelAndView) {
        if (StringUtils.isBlank(type)) {
            return ResultUtil.errorModel("type为空", modelAndView);
        }
        if (Constants.FormType.ADD.equals(type)) {
            modelAndView.addObject("type", "add");
            modelAndView.setViewName("flowerCode/flowerCodeForm");
            return modelAndView;
        } else {
            if (StringUtils.isBlank(typeId)) {
                return ResultUtil.errorModel("typeId为空", modelAndView);
            } else {
                TblFlowerCode flowerCode = flowerCodeService.findCodeById(typeId);
                modelAndView.addObject("flowerCode", flowerCode);
                modelAndView.addObject("type", "edit");
            }
            modelAndView.setViewName("flowerCode/flowerCodeForm");
            return modelAndView;
        }
    }

    @PostMapping("dataGrid")
    public Layui dataGrid(@Valid Page page, BindingResult bindingResult, TblFlowerCode flowerCode) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return Layui.error(validResult.getMsg());
        }
        return flowerCodeService.findCodeByPage(flowerCode, page);
    }

    @PostMapping("add")
    public ResultEntity add(@Valid TblFlowerCode flowerCode, BindingResult bindingResult) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        return flowerCodeService.addCode(flowerCode);
    }

    @PostMapping("del")
    public ResultEntity del (Integer typeId) {
        if (typeId == null) {
            return new ResultEntity(false, "typeId为空");
        }
        return flowerCodeService.delCode(typeId);
    }

    @PostMapping("edit")
    public ResultEntity edit(@Valid TblFlowerCode flowerCode, BindingResult bindingResult) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        return flowerCodeService.editCode(flowerCode);
    }
}
