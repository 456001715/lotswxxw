package com.lots.lots.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lots.lots.dao.mapper.LotsMenuMapper;
import com.lots.lots.entity.vo.LotsMenuNode;
import com.lots.lots.entity.vo.LotsMenuVo;
import com.lots.lots.service.LotsMenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台菜单表(LotsMenu)表服务实现类
 *
 * @author lots
 * @since 2021-05-07
 */
@Service
public class LotsMenuServiceImpl implements LotsMenuService {
    @Resource
    private LotsMenuMapper lotsMenuMapper;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public LotsMenuVo findById(Long id) {
        return this.lotsMenuMapper.findById(id);
    }

    /**
     * 新增数据
     *
     * @param lotsMenu 实例对象
     * @return 实例对象
     */
    @Override
    public int insert(LotsMenuVo lotsMenu) {
        return this.lotsMenuMapper.insert(lotsMenu);
    }

    /**
     * 修改数据
     *
     * @param lotsMenu 实例对象
     * @return 实例对象
     */
    @Override
    public int update(LotsMenuVo lotsMenu) {
        return this.lotsMenuMapper.update(lotsMenu);
    }

    /**
     * 通过主键删除数据
     *
     * @param ids 主键
     * @return 是否成功
     */
    @Override
    public Integer deleteByIds(String ids) {
        return this.lotsMenuMapper.deleteByIds(ids);
    }

    @Override
    public IPage<LotsMenuVo> pageList(LotsMenuVo lotsMenu, Integer pageSize, Integer pageNum) {
        Page page = new Page(pageNum,pageSize);
        final IPage<LotsMenuVo> lotsMenuVoIPage = lotsMenuMapper.queryAllPage(page,lotsMenu);
        return lotsMenuVoIPage;
    }

    @Override
    public List<LotsMenuNode> treeList() {
        List<LotsMenuVo> menuList = lotsMenuMapper.queryAll(new LotsMenuVo());
        List<LotsMenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return result;
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private LotsMenuNode covertMenuNode(LotsMenuVo menu, List<LotsMenuVo> menuList) {
        LotsMenuNode node = new LotsMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<LotsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        LotsMenuVo lotsMenu = new LotsMenuVo();
        lotsMenu.setId(id);
        lotsMenu.setHidden(hidden);
        return lotsMenuMapper.update(lotsMenu);
    }
}