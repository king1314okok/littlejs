package com.oudake.web.controller;

import com.oudake.web.model.TblFlower;
import com.oudake.web.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wangyi
 */
@RestController
public class IndexController extends BaseController {

    @Autowired
    private FlowerService flowerService;

    /**
     * 首页
     * @param modelAndView model
     * @return modelAndView
     */
    @GetMapping(value = {"", "index"})
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
        // 查找首页类型的花卉
        List<TblFlower> flowerList = flowerService.findIndexFlower();
        modelAndView.setViewName("index");
        modelAndView.addObject("flowerList", flowerList);
        setCodeList(request);
        return modelAndView;
    }

}
