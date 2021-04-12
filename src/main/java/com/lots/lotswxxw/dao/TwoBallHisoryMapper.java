package com.lots.lotswxxw.dao;

import com.lots.lotswxxw.domain.po.TwoBallHisoryPo;

import java.util.List;

/**
 * @name: TwoBallHisoryMapper
 * @author: lots
 * @date: 2020/9/18 14:31
 */
public interface TwoBallHisoryMapper {
    List<TwoBallHisoryPo> findTwoBallHisoryAll();
    List<TwoBallHisoryPo> findTwoBallHisoryByCondition(TwoBallHisoryPo po);
    List<TwoBallHisoryPo>findTwoBallHisoryByIds(List<Integer>id);
    int insertTwoBallHisory(TwoBallHisoryPo po);
}
