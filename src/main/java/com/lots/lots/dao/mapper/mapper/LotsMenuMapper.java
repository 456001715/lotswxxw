package com.lots.lots.dao.mapper.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lots.lots.entity.vo.LotsMenuVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (LotsMenu)表数据库访问层
 *
 * @author default
 * @since 2020-08-24 11:44:40
 */
public interface LotsMenuMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    LotsMenuVo findById(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param lotsMenu 实例对象
     * @return 对象列表
     */
    List<LotsMenuVo> queryAll(LotsMenuVo lotsMenu);

    /**
     * 通过实体作为筛选条件分页查询
     * @param page
     * @param lotsMenu
     * @return 对象列表
     */
    IPage<LotsMenuVo> queryAllPage(Page page, LotsMenuVo lotsMenu);

    /**
     * 新增数据
     *
     * @param lotsMenu 实例对象
     * @return 影响行数
     */
    int insert(LotsMenuVo lotsMenu);

    /**
     * 修改数据
     *
     * @param lotsMenu 实例对象
     * @return 影响行数
     */
    int update(LotsMenuVo lotsMenu);

    /**
     * 通过主键删除数据
     *
     * @param id
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据后台用户ID获取菜单
     *
     * @param userId: 用户ID
     * @author: lots
     * @return: 用户详情
     * @date: 2021/4/29 13:56
     */
    List<LotsMenuVo> getMenuList(@Param("userId") Long userId);

    /**
     * 通过主键删除数据
     *
     * @param ids
     * @return 影响行数
     */
    int deleteByIds(String ids);
}