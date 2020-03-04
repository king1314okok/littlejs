package com.oudake.web.controller;

import com.oudake.common.ResultEntity;
import com.oudake.web.model.TblFlower;
import com.oudake.web.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wangyi
 */
@RestController
@RequestMapping("favorite")
public class FavoriteController extends BaseController {

    @Autowired
    private FavoriteService favoriteService;

    @GetMapping("")
    public ModelAndView index(ModelAndView modelAndView, HttpServletRequest request) {
        modelAndView.addObject("favorite", viewFavorite(request));
        modelAndView.setViewName("user/favorite");
        return modelAndView;
    }

    @PostMapping("viewFavorite")
    public List<TblFlower> viewFavorite(HttpServletRequest request) {
        return (List<TblFlower>) favoriteService.findFavList(getCurrentUser(request).getUserId()).getObj();
    }

    @PostMapping("add")
    public ResultEntity add(Integer flowerId, HttpServletRequest request) {
        if (!isLogin(request).isSuccess()) {
            return new ResultEntity(false, "请先登录");
        }
        if (flowerId == null) {
            return new ResultEntity(false, "花卉id为空");
        }
        return favoriteService.addFlower(getCurrentUser(request).getUserId(), flowerId);
    }

    @PostMapping("del")
    public ResultEntity del(Integer flowerId, HttpServletRequest request) {
        if (flowerId == null) {
            return new ResultEntity(false, "花卉id为空");
        }
        return favoriteService.removeFlower(getCurrentUser(request).getUserId(), flowerId);
    }

}
