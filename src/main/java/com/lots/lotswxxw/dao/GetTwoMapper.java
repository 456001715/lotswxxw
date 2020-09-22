package com.lots.lotswxxw.dao;

import com.lots.lotswxxw.domain.po.GetTwoPO;

import java.util.List;

/**
 * @author: lots
 * @date: 2020/4/22 15:36
 * @description:
 */
public interface GetTwoMapper {

    /**
    * GetTwoMapper
    * @author: lots
    * @param entity:
    * @return: java.util.List<com.lots.lotswxxw.domain.po.GetTwoPO>
    * @date: 2020/9/22 11:26
    */
    List<GetTwoPO> findGetTwoByCondition(GetTwoPO entity);

    /**
    * GetTwoMapper
    * @author: lots
    * @param entity: 
    * @return: int
    * @date: 2020/9/22 11:25
    */
    int insertGetTwo(GetTwoPO entity);

    /**
    * GetTwoMapper
    * @author: lots
    * @param ids:
    * @return: int
    * @date: 2020/9/22 11:27
    */
    int deleteGetTwoByIds(List<Integer> ids);

    /**
    * GetTwoMapper
    * @author: lots
    * @param entity:
    * @return: int
    * @date: 2020/9/22 11:27
    */
    int updateGetTwo(GetTwoPO entity);

    /**
    * GetTwoMapper
    * @author: lots
    * @return: java.util.List<com.lots.lotswxxw.domain.po.GetTwoPO>
    * @date: 2020/9/22 11:27
    */
    List<GetTwoPO> getNowList();

}
