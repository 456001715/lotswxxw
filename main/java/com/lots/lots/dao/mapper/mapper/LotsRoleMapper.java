package com.lots.lots.dao.mapper.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lots.lots.entity.vo.LotsMenuVo;
import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.entity.vo.LotsRoleVo;

import java.util.List;

/**
 * (LotsRole)表数据库访问层
 *
 * @author default
 * @since 2020-08-24 11:44:40
 */
public interface LotsRoleMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    LotsRoleVo findById(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param lotsRole 实例对象
     * @return 对象列表
     */
    List<LotsRoleVo> queryAll(LotsRoleVo lotsRole);

    /**
     * 通过实体作为筛选条件分页查询
     * @param page
     * @param lotsRole
     * @return
     */
    IPage<LotsRoleVo> queryAllPage(Page page, LotsRoleVo lotsRole);

    /**
     * 新增数据
     *
     * @param lotsRole 实例对象
     * @return 影响行数
     */
    int insert(LotsRoleVo lotsRole);

    /**
     * 修改数据
     *
     * @param lotsRole 实例对象
     * @return 影响行数
     */
    int update(LotsRoleVo lotsRole);

    /**
     * 通过主键删除数据
     *
     * @param id
     * @return 影响行数
     */
    int deleteById(String id);

    /**
     * 通过主键批量删除数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByIds(List<Long> ids);

    /**
     * 获取角色相关菜单
     *
     * @param roleId:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsMenuVo>
     * @date: 2021/5/7 11:52
     */
    List<LotsMenuVo> getMenuListByRoleId(Long roleId);

    /**
     * 获取角色相关资源
     *
     * @param roleId:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsResourceVo>
     * @date: 2021/5/7 11:54
     */
    List<LotsResourceVo> getResourceListByRoleId(Long roleId);
}