package com.lots.lots.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lots.lots.entity.vo.LotsResourceVo;

import java.util.List;

/**
 * 后台资源表(LotsResource)表服务接口
 *
 * @author lots
 * @since 2021-04-28
 */
public interface LotsResourceService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LotsResourceVo findById(Long id);

    /**
     * 新增数据
     *
     * @param lotsResource 实例对象
     * @return 实例对象
     */
    int insert(LotsResourceVo lotsResource);

    /**
     * 修改数据
     *
     * @param lotsResource 实例对象
     * @return 实例对象
     */
    int update(LotsResourceVo lotsResource);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    int deleteById(Long id);

    /**
     * 查询全部资源
     *
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsResourceVo>
     * @date: 2021/4/28 18:05
     */
    List<LotsResourceVo> listAll();

    /**
     * 分页模糊查询后台资源
     * @param lotsResource
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage<LotsResourceVo> pageList(LotsResourceVo lotsResource, Integer pageSize, Integer pageNum);
}