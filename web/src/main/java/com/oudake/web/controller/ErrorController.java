package com.oudake.web.controller;

import com.oudake.web.util.ResultUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author wangyi
 */
@Controller
public class ErrorController {

    /**
     * 错误页面
     * @return 错误页面
     */
    @GetMapping("errorPage")
    public ModelAndView errorPage(ModelAndView modelAndView) {
        modelAndView.setViewName("errorPage");
        return modelAndView;
    }

    @GetMapping("404")
    public ModelAndView error404(ModelAndView modelAndView) {
        return ResultUtil.errorModel("404, 该页面不存在", modelAndView);
    }

    @GetMapping("500")
    public ModelAndView error500(ModelAndView modelAndView) {
        return ResultUtil.errorModel("500，服务器内部错误", modelAndView);
    }
}
