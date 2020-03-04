package com.oudake.web.controller;

import com.oudake.web.model.TblSysCode;
import com.oudake.web.service.SysCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author wangyi
 */
@Controller
@RequestMapping("sysCode")
public class SysCodeController {

    @Autowired
    private SysCodeService sysCodeService;

    @RequestMapping("findSysCodeByType")
    @ResponseBody
    public List<TblSysCode> findSysCodeByType(String type) {
        return sysCodeService.findSysCodeByType(type);
    }
}
