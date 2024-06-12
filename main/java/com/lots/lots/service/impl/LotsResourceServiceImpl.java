package com.lots.lots.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lots.lots.dao.mapper.LotsResourceMapper;
import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.service.LotsResourceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 后台资源表(LotsResource)表服务实现类
 *
 * @author lots
 * @since 2021-04-28
 */
@Service
public class LotsResourceServiceImpl implements LotsResourceService {
    @Resource
    private LotsResourceMapper lotsResourceMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public LotsResourceVo findById(Long id) {
        return this.lotsResourceMapper.findById(id);
    }

    /**
     * 新增数据
     *
     * @param lotsResource 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(LotsResourceVo lotsResource) {

        return this.lotsResourceMapper.insert(lotsResource);
    }

    /**
     * 修改数据
     *
     * @param lotsResource 实例对象
     * @return 实例对象
     */
    @Override
    public int update(LotsResourceVo lotsResource) {

        return this.lotsResourceMapper.update(lotsResource);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public int deleteById(Long id) {
        return this.lotsResourceMapper.deleteById(id);
    }

    /**
     * 查询全部资源
     *
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsResourceVo>
     * @date: 2021/4/28 18:05
     */
    @Override
    public List<LotsResourceVo> listAll() {
        return lotsResourceMapper.queryAll(new LotsResourceVo());
    }

    @Override
    public IPage<LotsResourceVo> pageList(LotsResourceVo lotsResource, Integer pageSize, Integer pageNum) {
        return lotsResourceMapper.queryAllPage(new Page(pageNum,pageSize),lotsResource);
    }
}