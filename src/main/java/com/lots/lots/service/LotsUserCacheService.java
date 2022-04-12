package com.lots.lots.service;


import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.entity.vo.LotsUserVo;

import java.util.List;

/**
 * 后台用户缓存操作类
 *
 * @author lots
 */
public interface LotsUserCacheService {
    /**
     * 删除后台用户缓存
     *
     * @param username: 用户名
     * @author: lots
     * @return: void
     * @date: 2021/4/29 13:56
     */
    void delAdmin(String username);

    /**
     * 删除后台用户资源列表缓存
     *
     * @param userId: 用户ID
     * @author: lots
     * @return: void
     * @date: 2021/4/29 13:57
     */
    void delResourceList(Long userId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     *
     * @param roleId: 角色ID
     * @author: lots
     * @return: void
     * @date: 2021/4/29 13:57
     */
    void delResourceListByRole(Long roleId);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     *
     * @param roleIds: 角色IDS
     * @author: lots
     * @return: void
     * @date: 2021/4/29 13:57
     */
    void delResourceListByRoleIds(List<Long> roleIds);

    /**
     * 当资源信息改变时，删除资源项目后台用户缓存
     *
     * @param resourceId: 资源ID
     * @author: lots
     * @return: void
     * @date: 2021/4/29 13:57
     */
    void delResourceListByResource(Long resourceId);

    /**
     * 获取缓存后台用户信息
     *
     * @param username: 用户名
     * @author: lots
     * @return: com.lots.lots.entity.vo.LotsUserVo
     * @date: 2021/4/29 13:57
     */
    LotsUserVo getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     *
     * @param admin: 用户详情
     * @author: lots
     * @return: void
     * @date: 2021/4/29 13:58
     */
    void setAdmin(LotsUserVo admin);

    /**
     * 获取缓存后台用户资源列表
     *
     * @param userId: 用户ID
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsResourceVo>
     * @date: 2021/4/29 13:58
     */
    List<LotsResourceVo> getResourceList(Long userId);

    /**
     * 设置后台后台用户资源列表
     *
     * @param userId:       用户ID
     * @param resourceList: 资源列表
     * @author: lots
     * @return: void
     * @date: 2021/4/29 13:58
     */
    void setResourceList(Long userId, List<LotsResourceVo> resourceList);
}
