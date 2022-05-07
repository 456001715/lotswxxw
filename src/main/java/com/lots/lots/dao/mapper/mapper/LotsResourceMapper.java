package com.lots.lots.dao.mapper.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lots.lots.entity.vo.LotsResourceVo;

import java.util.List;

/**
 * (LotsResource)表数据库访问层
 *
 * @author default
 * @since 2020-08-24 11:44:40
 */
public interface LotsResourceMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    LotsResourceVo findById(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param lotsResource 实例对象
     * @return 对象列表
     */
    List<LotsResourceVo> queryAll(LotsResourceVo lotsResource);

    /**
     * 通过实体作为筛选条件分页查询
     * @param page
     * @param lotsResource
     * @return
     */
    IPage<LotsResourceVo> queryAllPage (Page page, LotsResourceVo lotsResource);
    /**
     * 新增数据
     *
     * @param lotsResource 实例对象
     * @return 影响行数
     */
    int insert(LotsResourceVo lotsResource);

    /**
     * 修改数据
     *
     * @param lotsResource 实例对象
     * @return 影响行数
     */
    int update(LotsResourceVo lotsResource);

    /**
     * 通过主键删除数据
     *
     * @param id
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * LotsResourceMapper
     *
     * @param id:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsResourceVo>
     * @date: 2021/4/29 14:05
     */
    List<LotsResourceVo> getResourceList(Long id);
}