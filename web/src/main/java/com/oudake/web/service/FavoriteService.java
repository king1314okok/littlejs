package com.oudake.web.service;

import com.oudake.common.ResultEntity;

/**
 * @author wangyi
 */
public interface FavoriteService {
    ResultEntity addFlower(Integer userId, Integer flowerId);

    ResultEntity findFavList(Integer userId);

    ResultEntity removeFlower(Integer userId, Integer flowerId);
}
