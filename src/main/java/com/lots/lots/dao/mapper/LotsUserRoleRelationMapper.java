package com.lots.lots.dao.mapper;

import com.lots.lots.entity.vo.LotsPermissionVo;
import com.lots.lots.entity.vo.LotsRoleVo;
import com.lots.lots.entity.vo.LotsUserRoleRelationVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (LotsUserRoleRelation)表数据库访问层
 *
 * @author default
 * @since 2020-08-24 11:44:40
 */
public interface LotsUserRoleRelationMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    LotsUserRoleRelationVo findById(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param lotsUserRoleRelation 实例对象
     * @return 对象列表
     */
    List<LotsUserRoleRelationVo> queryAll(LotsUserRoleRelationVo lotsUserRoleRelation);

    /**
     * 新增数据
     *
     * @param lotsUserRoleRelation 实例对象
     * @return 影响行数
     */
    int insert(LotsUserRoleRelationVo lotsUserRoleRelation);

    /**
     * 修改数据
     *
     * @param lotsUserRoleRelation 实例对象
     * @return 影响行数
     */
    int update(LotsUserRoleRelationVo lotsUserRoleRelation);

    /**
     * 通过主键删除数据
     *
     * @param id
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * LotsUserRoleRelationMapper
     *
     * @param roleIds:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsUserRoleRelationVo>
     * @date: 2021/4/28 17:52
     */
    List<LotsUserRoleRelationVo> findRoleIdIn(List<Long> roleIds);

    /**
     * 获取资源相关用户ID列表
     *
     * @param resourceId:资源ID
     * @author: lots
     * @return: java.util.List<java.lang.Long>
     * @date: 2021/4/29 10:34
     */
    List<Long> getUserIdList(@Param("resourceId") Long resourceId);

    /**
     * 获取用于所有角色
     *
     * @param userId:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsRoleVo>
     * @date: 2021/4/29 10:34
     */
    List<LotsRoleVo> getRoleList(@Param("userId") Long userId);

    /**
     * 通过主键删除数据
     *
     * @param userId
     * @return 影响行数
     */
    int deleteByUserId(@Param("userId") Long userId);

    /**
     * 批量插入用户角色关系
     *
     * @param adminRoleRelationList:
     * @author: lots
     * @return: int
     * @date: 2021/4/29 11:05
     */
    int insertList(@Param("list") List<LotsUserRoleRelationVo> adminRoleRelationList);

    /**
     * 获取用户所有权限(包括+-权限)
     *
     * @param userId:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsPermissionVo>
     * @date: 2021/4/29 11:27
     */
    List<LotsPermissionVo> getPermissionList(@Param("userId") Long userId);
}