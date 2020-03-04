package com.oudake.web.controller;

import com.oudake.common.constants.Constants;
import com.oudake.web.model.TblFlower;
import com.oudake.web.service.FlowerCodeService;
import com.oudake.web.service.FlowerService;
import com.oudake.web.service.SysCodeService;
import com.oudake.web.util.ResultUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Author wangyi
 * @Description 花卉商品页面
 * @Date 2019/1/4 19:11
 * @Version 1.0
 */
@RestController
@RequestMapping("flower")
public class FlowerController extends BaseController {

    private static final String CODE_EXIST_NAME = "codeExist";
    private static final String SEARCH_MODE_NAME = "searchMode";

    @Autowired
    private FlowerService flowerService;
    @Autowired
    private FlowerCodeService flowerCodeService;
    @Autowired
    private SysCodeService sysCodeService;

    @GetMapping()
    public ModelAndView index(ModelAndView modelAndView) {
        return ResultUtil.errorModel("404", modelAndView);
    }

    /**
     * @Author wangyi
     * @Description // 单个花卉页面
     * @Date 2019/1/21
     * @Param [flowerId, model]
     * @return java.lang.String
    **/
    @GetMapping("{flowerId}")
    public ModelAndView flower(@PathVariable(value = "flowerId") Integer flowerId, ModelAndView modelAndView, HttpServletRequest request) {
        if (flowerId == null) {
            return ResultUtil.errorModel("花卉Id不能为空", modelAndView);
        } else {
            TblFlower flower = flowerService.findByFlowerId(flowerId);
            String normalCode = sysCodeService.findSysCodeByTypeAndName(Constants.FlowerStatus.NAME, Constants.FlowerStatus.NORMAL).getDisplay1();
            if (!flower.getFlowerStatus().equals(normalCode)) {
                return ResultUtil.errorModel("该商品已下架", modelAndView);
            }
            flowerService.addViewCount(flower.getFlowerId());
            modelAndView.addObject("flower", flower);
        }
        modelAndView.setViewName("flower");
        setCodeList(request);
        return modelAndView;
    }

    /**
     * @Author wangyi
     * @Description // 花卉搜索结果页面
     * @Date 2019/1/21
     * @Param [flowerName, typeName, model]
     * @return java.lang.String
    **/
    @RequestMapping("flowerList")
    public ModelAndView list(String keyword, String searchMode, ModelAndView modelAndView, HttpServletRequest request) {
        if (StringUtils.isBlank(keyword)) {
            keyword = "";
        } else {
            keyword = keyword.trim();
            // 空格变+
            keyword = keyword.replace(" ", "+");
            String[] temp = keyword.split("\\+");
            for (String str : temp) {
                if (flowerCodeService.isCodeExist(str)) {
                    modelAndView.addObject(CODE_EXIST_NAME, keyword);
                }
            }
        }
        if (StringUtils.isBlank(searchMode)) {
            searchMode = "";
        }
        switch (searchMode) {
            case "sale":
                request.getSession().setAttribute(SEARCH_MODE_NAME, "sale");
                List<TblFlower> saleList = flowerService.sortBySaleCount(flowerService.findByKeyword(keyword));
                modelAndView.addObject("flowerList", saleList);
                break;
            case "popular":
                request.getSession().setAttribute(SEARCH_MODE_NAME, "popular");
                List<TblFlower> popularList = flowerService.sortByViewCount(flowerService.findByKeyword(keyword));
                modelAndView.addObject("flowerList", popularList);
                break;
            case "price":
                request.getSession().setAttribute(SEARCH_MODE_NAME, "price");
                List<TblFlower> priceList = flowerService.sortByPrice(flowerService.findByKeyword(keyword));
                modelAndView.addObject("flowerList", priceList);
                break;
            default:
                request.getSession().setAttribute(SEARCH_MODE_NAME, "default");
                List<TblFlower> defaultList = flowerService.findByKeyword(keyword);
                modelAndView.addObject("flowerList", defaultList);
                break;
        }
        modelAndView.setViewName("flowerList");
        setCodeList(request);
        return modelAndView;
    }
}
