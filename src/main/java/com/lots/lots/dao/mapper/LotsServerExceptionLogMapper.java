package com.lots.lots.dao.mapper;

import com.lots.lots.entity.vo.LotsServerExceptionLogVo;

import java.util.List;

/**
 * (LotsServerExceptionLog)表数据库访问层
 *
 * @author default
 * @since 2020-08-24 11:44:40
 */
public interface LotsServerExceptionLogMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param logid
     * @return 实例对象
     */
    LotsServerExceptionLogVo findById(String logid);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param lotsServerExceptionLog 实例对象
     * @return 对象列表
     */
    List<LotsServerExceptionLogVo> queryAll(LotsServerExceptionLogVo lotsServerExceptionLog);

    /**
     * 新增数据
     *
     * @param lotsServerExceptionLog 实例对象
     * @return 影响行数
     */
    int insert(LotsServerExceptionLogVo lotsServerExceptionLog);

    /**
     * 修改数据
     *
     * @param lotsServerExceptionLog 实例对象
     * @return 影响行数
     */
    int update(LotsServerExceptionLogVo lotsServerExceptionLog);

    /**
     * 通过主键删除数据
     *
     * @param logid
     * @return 影响行数
     */
    int deleteById(String logid);

}