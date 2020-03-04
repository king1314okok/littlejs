package com.oudake.console.controller;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.console.model.TblFlower;
import com.oudake.console.service.FlowerService;
import com.oudake.console.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import java.util.List;

/**
 * @Author wangyi
 * @Description 花卉管理
 * @Date 2019/1/13 18:01
 * @Version 1.0
 */
@Controller
@RequestMapping("flower")
public class FlowerController {

    @Autowired
    private FlowerService flowerService;

    @RequestMapping()
    public String index() {
        return "flower/flower";
    }

    @RequestMapping("form")
    public String form(String type, String flowerId, Model model) {
        if (StringUtils.isBlank(type)) {
            return ResultUtil.errorModel("type为空", model);
        }
        if (Constants.FormType.ADD.equals(type)) {
            model.addAttribute("type", "add");
            return "flower/flowerForm";
        } else {
            if (StringUtils.isBlank(flowerId)) {
                return ResultUtil.errorModel("花卉id为空", model);
            } else {
                List<TblFlower> flowerList = flowerService.findFlowerById(flowerId);
                model.addAttribute("type", "edit");
                model.addAttribute("flower", flowerList.get(0));
            }
            return "flower/flowerForm";
        }
    }

    @RequestMapping("dataGrid")
    @ResponseBody
    public Layui dataGrid(@Valid Page page, BindingResult bindingResult,
                          TblFlower flower) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return Layui.error(validResult.getMsg());
        }
        return flowerService.findFlowerByPage(flower, page);
    }

    @RequestMapping("add")
    @ResponseBody
    public ResultEntity doAdd(@Valid TblFlower flower, BindingResult bindingResult) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        return flowerService.addFlower(flower);
    }

    @RequestMapping("edit")
    @ResponseBody
    public ResultEntity edit(@Valid TblFlower flower, BindingResult bindingResult) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        return flowerService.editFlower(flower);
    }

    @RequestMapping("del")
    @ResponseBody
    public ResultEntity del(Integer flowerId) {
        if (flowerId == null) {
            return new ResultEntity(false, "花卉id为空");
        }
        return flowerService.delFlower(flowerId);
    }

}
