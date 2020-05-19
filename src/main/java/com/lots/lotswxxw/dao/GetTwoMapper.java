package com.lots.lotswxxw.dao;

import com.lots.lotswxxw.domain.po.GetTwoPO;

import java.util.List;

/**
 * @author: lots
 * @date: 2020/4/22 15:36
 * @description:
 */
public interface GetTwoMapper {
    List<GetTwoPO> findGetTwoByCondition(GetTwoPO entity);
    int insertGetTwo(GetTwoPO entity);
    int deleteGetTwoByIds(List<Integer>ids);
    int updateGetTwo(GetTwoPO entity);

}
