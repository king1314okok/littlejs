package com.oudake.console.controller;

import com.oudake.common.Layui;
import com.oudake.common.Page;
import com.oudake.common.ResultEntity;
import com.oudake.common.constants.Constants;
import com.oudake.console.model.TblSysCode;
import com.oudake.console.service.SysCodeService;
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
 * @author wangyi
 */
@Controller
@RequestMapping("sysCode")
public class SysCodeController {

    @Autowired
    private SysCodeService sysCodeService;

    @RequestMapping()
    public String index() {
        return "sysCode/sysCode";
    }

    @RequestMapping("form")
    public String form(String actionType, TblSysCode sysCode, Model model) {
        if (StringUtils.isBlank(actionType)) {
            return ResultUtil.errorModel("404", model);
        }
        if (Constants.FormType.ADD.equals(actionType)) {
            model.addAttribute("type", "add");
            return "sysCode/sysCodeForm";
        } else {
            if (StringUtils.isBlank(sysCode.getType())) {
                return ResultUtil.errorModel("类型为空", model);
            } else if (StringUtils.isBlank(sysCode.getCode())) {
                return ResultUtil.errorModel("代码为空", model);
            } else {
                TblSysCode resultCode = null;
                resultCode = sysCodeService.findSysCodeByTypeAndName(sysCode.getType(), sysCode.getCode());
                model.addAttribute("type", "edit");
                model.addAttribute("sysCode", resultCode);
            }
            return "sysCode/sysCodeForm";
        }
    }

    @RequestMapping("dataGrid")
    @ResponseBody
    public Layui dataGrid(@Valid Page page, BindingResult bindingResult,
                          TblSysCode sysCode) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return Layui.error(validResult.getMsg());
        }
        return sysCodeService.findSysCodeByPage(sysCode, page);
    }

    @RequestMapping("add")
    @ResponseBody
    public ResultEntity add(@Valid TblSysCode sysCode, BindingResult bindingResult) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        return sysCodeService.addSysCode(sysCode);
    }

    @RequestMapping("edit")
    @ResponseBody
    public ResultEntity edit(@Valid TblSysCode sysCode, BindingResult bindingResult) {
        ResultEntity validResult = ResultUtil.validModel(bindingResult);
        if (!validResult.isSuccess()) {
            return validResult;
        }
        return sysCodeService.editSysCode(sysCode);
    }

    @RequestMapping("del")
    @ResponseBody
    public ResultEntity del(String type, String code) {
        if (StringUtils.isBlank(type)) {
            return new ResultEntity(false, "类型不能为空");
        }
        if (StringUtils.isBlank(code)) {
            return new ResultEntity(false, "代码不能为空");
        }
        return sysCodeService.delSysCode(type, code);
    }

    @RequestMapping("findSysCodeByType")
    @ResponseBody
    public List<TblSysCode> findSysCodeByType(String type) {
        return sysCodeService.findSysCodeByType(type);
    }

    @RequestMapping("findSysCodeByTypeAndCode")
    @ResponseBody
    public ResultEntity findSysCodeByTypeAndCode(TblSysCode sysCode) {
        if (sysCode.getType() == null || "".equals(sysCode.getType())) {
            return new ResultEntity(false, "type为空");
        }
        if (sysCode.getCode() == null || "".equals(sysCode.getCode())) {
            return new ResultEntity(false, "code为空");
        }
        String display1 = sysCodeService.findSysCodeByTypeAndName(sysCode.getType(), sysCode.getCode()).getDisplay1();
        return new ResultEntity(true, "获取参数成功", display1);
    }
}
