package com.lots.lots.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lots.lots.entity.vo.LotsMenuNode;
import com.lots.lots.entity.vo.LotsMenuVo;

import java.util.List;

/**
 * 后台菜单表(LotsMenu)表服务接口
 *
 * @author lots
 * @since 2021-05-07
 */
public interface LotsMenuService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LotsMenuVo findById(Long id);

    /**
     * 新增数据
     *
     * @param lotsMenu 实例对象
     * @return 实例对象
     */
    int insert(LotsMenuVo lotsMenu);

    /**
     * 修改数据
     *
     * @param lotsMenu 实例对象
     * @return 实例对象
     */
    int update(LotsMenuVo lotsMenu);

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    Integer deleteByIds(String ids);

    /**
     * 分页查询后台菜单
     * @param lotsMenu
     * @param pageSize
     * @param pageNum
     * @return
     */
    IPage<LotsMenuVo> pageList(LotsMenuVo lotsMenu, Integer pageSize, Integer pageNum);

    /**
     * 树形结构返回所有菜单列表
     *
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsMenuNode>
     * @date: 2021/5/7 19:00
     */
    List<LotsMenuNode> treeList();

    /**
     * 修改菜单显示状态
     *
     * @param id:
     * @param hidden:
     * @author: lots
     * @return: int
     * @date: 2021/5/7 19:03
     */
    int updateHidden(Long id, Integer hidden);
}