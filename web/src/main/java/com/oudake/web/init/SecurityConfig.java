package com.oudake.web.init;

/**
 * @Author wangyi
 * @Description SecurityConfig
 * @Date 2019/1/24 18:22
 * @Version 1.0
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Autowired
    private AuthInterceptor authInterceptor;


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration addInterceptor = registry.addInterceptor(authInterceptor);
        addInterceptor.addPathPatterns("/**");
        addInterceptor.excludePathPatterns("/static/**");
        addInterceptor.excludePathPatterns("/errorPage");

        // 错误页面
        addInterceptor.excludePathPatterns("/404");
        addInterceptor.excludePathPatterns("/500");

        // 验证码
        addInterceptor.excludePathPatterns("/captcha");

        // 首页 + 商品页面
        addInterceptor.excludePathPatterns("/");
        addInterceptor.excludePathPatterns("/index/**");
        addInterceptor.excludePathPatterns("/flower/**");
        addInterceptor.excludePathPatterns("/flowerList");
        addInterceptor.excludePathPatterns("/flowerCode/**");

        // 注册
        addInterceptor.excludePathPatterns("/register");
        addInterceptor.excludePathPatterns("/doRegister**");

        // 登录
        addInterceptor.excludePathPatterns("/login");
        addInterceptor.excludePathPatterns("/doLogin/**");
        addInterceptor.excludePathPatterns("/logout/**");

        // 找回密码
        addInterceptor.excludePathPatterns("/findPwd");
        addInterceptor.excludePathPatterns("/doFindPwd");

        // 购物车
        addInterceptor.excludePathPatterns("/cart/**");
        addInterceptor.excludePathPatterns("/gatewayOrder/place**");

        // 收藏夹
        addInterceptor.excludePathPatterns("/favorite/add");
    }
}
