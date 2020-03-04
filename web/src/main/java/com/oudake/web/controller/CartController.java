package com.oudake.web.controller;

import com.oudake.common.ResultEntity;
import com.oudake.web.model.TblFlower;
import com.oudake.web.service.FlowerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangyi
 */
@RestController
@RequestMapping("cart")
public class CartController extends BaseController {

    // 该list为flower类型
    private final String CART_SESSION = "cart";

    @Autowired
    private FlowerService flowerService;

    @GetMapping()
    public ModelAndView index(ModelAndView modelAndView, HttpSession session) {
        setCartSession(session);
        modelAndView.setViewName("cart");
        return modelAndView;
    }

    @PostMapping("viewCart")
    public ResultEntity viewCart(HttpSession session) {
        int cartSize = getCartSize(session);
        if (cartSize == 0) {
            return new ResultEntity(false, "该购物车中无商品");
        } else {
            return new ResultEntity(true, "目前有 " + cartSize + " 件商品", (List<TblFlower>)session.getAttribute(CART_SESSION));
        }
    }


    @PostMapping("add")
    public ResultEntity add(TblFlower flower, HttpSession session) {
        if (!isCartExist(session)) {
            setCartSession(session);
        }
        if (flower.getFlowerId() == null || "".equals(flower.getFlowerId())) {
            return new ResultEntity(false, "flowerId不能为空");
        }
        if (flower.getAmount() == null || flower.getAmount() <= 0) {
            return new ResultEntity(false, "数量非法");
        }

        // 根据flowerId查找flower
        int amount = flower.getAmount();
        TblFlower tempFlower = flowerService.findByFlowerId(flower.getFlowerId());
        if (tempFlower == null) {
            return new ResultEntity(false, "该花卉不存在");
        } else {
            tempFlower.setAmount(amount);
        }

        List<TblFlower> cartList = (List<TblFlower>) session.getAttribute(CART_SESSION);

        // 删除重复物品
        for (TblFlower temp : cartList) {
            if (temp.getFlowerId().equals(tempFlower.getFlowerId())) {
                cartList.remove(temp);
                break;
            }
        }

        cartList.add(tempFlower);
        session.setAttribute(CART_SESSION, cartList);
        return new ResultEntity(true, "已添加到购物车");
    }

    @PostMapping("del")
    public ResultEntity del(Integer flowerId, HttpSession session) {
        if (!isCartExist(session)) {
            setCartSession(session);
        }
        List<TblFlower> cartList = (List<TblFlower>) session.getAttribute(CART_SESSION);
        for (TblFlower flower : cartList) {
            if (flowerId.equals(flower.getFlowerId())) {
                cartList.remove(flower);
                session.setAttribute(CART_SESSION, cartList);
                return new ResultEntity(true, "已从购物车中移除");
            }
        }
        return new ResultEntity(false, "该购物车中无此商品");
    }

    private boolean isCartExist(HttpSession session) {
        return session.getAttribute(CART_SESSION) != null;
    }

    private void setCartSession(HttpSession session) {
        if (!isCartExist(session)) {
            List<TblFlower> cartList = new ArrayList<>();
            session.setAttribute(CART_SESSION, cartList);
        }
    }

    private int getCartSize(HttpSession session) {
        List<TblFlower> cartList = (List<TblFlower>) session.getAttribute(CART_SESSION);
        if (!isCartExist(session) || cartList.size() == 0) {
            return 0;
        } else {
            return cartList.size();
        }
    }
}
