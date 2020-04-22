package com.lots.lotswxxw.dao;

import com.lots.lotswxxw.domain.po.ListenHisoryEntity;

import java.util.List;

/**
 * @author: lots
 * @date: 2020/4/22 15:36
 * @description:
 */
public interface ListenHisoryDao {
    List<ListenHisoryEntity> findListenHisoryAll();
    List<ListenHisoryEntity> findListenHisoryByCondition(ListenHisoryEntity entity);
    int insertListenHisory(ListenHisoryEntity entity);
    int insertListenHisorys(List<ListenHisoryEntity>list);
    int deleteListenHisoryById(int id);
    int deleteListenHisoryByIds(List<Integer>ids);

}
