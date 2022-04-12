package com.lots.lots.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lots.lots.entity.vo.LotsMenuVo;
import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.entity.vo.LotsRoleVo;

import java.util.List;

/**
 * 后台用户角色表(LotsRole)表服务接口
 *
 * @author lots
 * @since 2021-04-28
 */
public interface LotsRoleService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LotsRoleVo findById(Long id);

    /**
     * 新增数据
     *
     * @param lotsRole 实例对象
     * @return 实例对象
     */
    LotsRoleVo insert(LotsRoleVo lotsRole);

    /**
     * 修改数据
     *
     * @param lotsRole 实例对象
     * @return 实例对象
     */
    Integer update(LotsRoleVo lotsRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据管理员ID获取对应菜单
     *
     * @param userId: 用户ID
     * @author: lots
     * @return: 菜单详情
     * @date: 2021/4/29 13:56
     */
    List<LotsMenuVo> getMenuList(Long userId);

    /**
     * 添加角色
     *
     * @param role:
     * @author: lots
     * @return: int
     * @date: 2021/5/7 10:43
     */
    int create(LotsRoleVo role);

    /**
     * 批量删除角色
     *
     * @param ids:
     * @author: lots
     * @return: int
     * @date: 2021/5/7 10:47
     */
    int deleteByIds(String ids);

    /**
     * 查询所有角色
     *
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsRoleVo>
     * @date: 2021/5/7 11:34
     */
    List<LotsRoleVo> findAll();

    /**
     * 根据角色名称分页获取角色列表
     *
     * @param keyword:
     * @param pageSize:
     * @param pageNum:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsRoleVo>
     * @date: 2021/5/7 11:44
     */
    IPage<LotsRoleVo> pageList(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 获取角色相关菜单
     *
     * @param roleId:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsMenuVo>
     * @date: 2021/5/7 11:50
     */
    List<LotsMenuVo> getMenuByRoleId(Long roleId);

    /**
     * 获取角色相关资源
     *
     * @param roleId:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsResourceVo>
     * @date: 2021/5/7 11:54
     */
    List<LotsResourceVo> listResource(Long roleId);

    /**
     * 给角色分配菜单
     *
     * @param roleId:
     * @param menuIds:
     * @author: lots
     * @return: int
     * @date: 2021/5/7 11:55
     */
    int allocMenu(Long roleId, String menuIds);

    /**
     * 给角色分配资源
     *
     * @param roleId:
     * @param resourceIds:
     * @author: lots
     * @return: int
     * @date: 2021/5/7 14:02
     */
    int allocResource(Long roleId, String resourceIds);
}