package com.lots.lots.dao.mapper.mapper;

import com.lots.lots.entity.vo.LotsUserLoginLogVo;

import java.util.List;

/**
 * (LotsUserLoginLog)表数据库访问层
 *
 * @author default
 * @since 2020-08-24 11:44:40
 */
public interface LotsUserLoginLogMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    LotsUserLoginLogVo findById(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param lotsUserLoginLog 实例对象
     * @return 对象列表
     */
    List<LotsUserLoginLogVo> queryAll(LotsUserLoginLogVo lotsUserLoginLog);

    /**
     * 新增数据
     *
     * @param lotsUserLoginLog 实例对象
     * @return 影响行数
     */
    int insert(LotsUserLoginLogVo lotsUserLoginLog);

    /**
     * 修改数据
     *
     * @param lotsUserLoginLog 实例对象
     * @return 影响行数
     */
    int update(LotsUserLoginLogVo lotsUserLoginLog);

    /**
     * 通过主键删除数据
     *
     * @param id
     * @return 影响行数
     */
    int deleteById(Long id);

}