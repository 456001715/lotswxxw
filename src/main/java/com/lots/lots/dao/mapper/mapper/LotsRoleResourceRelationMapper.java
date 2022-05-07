package com.lots.lots.dao.mapper.mapper;

import com.lots.lots.entity.vo.LotsRoleResourceRelationVo;

import java.util.List;

/**
 * (LotsRoleResourceRelation)表数据库访问层
 *
 * @author default
 * @since 2020-08-24 11:44:40
 */
public interface LotsRoleResourceRelationMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    LotsRoleResourceRelationVo findById(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param lotsRoleResourceRelation 实例对象
     * @return 对象列表
     */
    List<LotsRoleResourceRelationVo> queryAll(LotsRoleResourceRelationVo lotsRoleResourceRelation);

    /**
     * 新增数据
     *
     * @param lotsRoleResourceRelation 实例对象
     * @return 影响行数
     */
    int insert(LotsRoleResourceRelationVo lotsRoleResourceRelation);

    /**
     * 批量新增数据
     *
     * @param lotsRoleResourceRelationList 实例对象
     * @return 影响行数
     */
    int insertList(List<LotsRoleResourceRelationVo> lotsRoleResourceRelationList);

    /**
     * 修改数据
     *
     * @param lotsRoleResourceRelation 实例对象
     * @return 影响行数
     */
    int update(LotsRoleResourceRelationVo lotsRoleResourceRelation);

    /**
     * 通过主键删除数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByIds(String ids);

    /**
     * 根据roleId删除资源
     *
     * @param roleId:
     * @author: lots
     * @return: void
     * @date: 2021/5/7 14:05
     */
    void deleteByRoleId(Long roleId);
}