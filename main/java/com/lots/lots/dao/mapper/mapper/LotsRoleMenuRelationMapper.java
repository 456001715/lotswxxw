package com.lots.lots.dao.mapper.mapper;

import com.lots.lots.entity.vo.LotsRoleMenuRelationVo;

import java.util.List;

/**
 * (LotsRoleMenuRelation)表数据库访问层
 *
 * @author default
 * @since 2020-08-24 11:44:40
 */
public interface LotsRoleMenuRelationMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    LotsRoleMenuRelationVo findById(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param lotsRoleMenuRelation 实例对象
     * @return 对象列表
     */
    List<LotsRoleMenuRelationVo> queryAll(LotsRoleMenuRelationVo lotsRoleMenuRelation);

    /**
     * 新增数据
     *
     * @param lotsRoleMenuRelation 实例对象
     * @return 影响行数
     */
    int insert(LotsRoleMenuRelationVo lotsRoleMenuRelation);

    /**
     * 批量新增数据
     *
     * @param lotsRoleMenuRelationList 实例对象
     * @return 影响行数
     */
    int insertList(List<LotsRoleMenuRelationVo> lotsRoleMenuRelationList);

    /**
     * 修改数据
     *
     * @param lotsRoleMenuRelation 实例对象
     * @return 影响行数
     */
    int update(LotsRoleMenuRelationVo lotsRoleMenuRelation);

    /**
     * 通过主键删除数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByIds(String ids);

    /**
     * 通过主键删除数据
     *
     * @param roleId
     * @return 影响行数
     */
    int deleteByRoleId(Long roleId);
}