package com.lots.lots.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.lots.lots.common.JsonResult;
import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.entity.vo.LotsRoleVo;
import com.lots.lots.entity.vo.LotsUser.RegisterParam;
import com.lots.lots.entity.vo.LotsUser.UpdateParam;
import com.lots.lots.entity.vo.LotsUserVo;
import com.lots.lots.entity.vo.UpdateAdminPasswordParam;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台用户表(LotsUser)表服务接口
 *
 * @author lots
 * @since 2021-04-28
 */
public interface LotsUserService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    LotsUserVo findById(Long id);

    /**
     * 新增数据
     *
     * @param lotsUser 实例对象
     * @return 实例对象
     */
    LotsUserVo insert(LotsUserVo lotsUser);

    /**
     * 修改数据
     *
     * @param lotsUser 实例对象
     * @return 实例对象
     */
    Integer update(UpdateParam lotsUser);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 获取全部资源
     *
     * @param userId
     * @author: lots
     * @return: List
     * @date: 2021/4/28 17:40
     */
    List<LotsResourceVo> getResourceList(Long userId);

    /**
     * 根据用户名获取数据
     *
     * @param username
     * @author: lots
     * @return: com.lots.lots.entity.vo.LotsUserVo
     * @date: 2021/4/28 17:40
     */
    LotsUserVo getAdminByUsername(String username);

    /**
     * 注册
     *
     * @param registerParam:
     * @author: lots
     * @return: com.lots.lots.entity.vo.LotsUserVo
     * @date: 2021/4/29 9:57
     */
    JsonResult register(RegisterParam registerParam);

    /**
     * 登录功能
     *
     * @param username 用户名
     * @param password 密码
     * @return 生成的JWT的token
     */
    String login(String username, String password);


    /**
     * 刷新token的功能
     *
     * @param oldToken 旧的token
     * @author: lots
     * @return: 新的token
     * @date: 2021/4/29 10:18
     */
    String refreshToken(String oldToken);

    /**
     * 获取用户对于角色
     *
     * @param userId:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsRoleVo>
     * @date: 2021/4/29 10:29
     */
    List<LotsRoleVo> getRoleList(Long userId);

    /**
     * 根据用户名或昵称分页查询用户
     *
     * @param keyword:  关键词
     * @param pageSize: 页数
     * @param pageNum:  每页数量
     * @author: lots
     * @return: java.util.List<LotsUserVo>
     * @date: 2021/4/29 10:37
     */
    IPage<LotsUserVo> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 修改密码
     *
     * @param updatePasswordParam:
     * @author: lots
     * @return: int
     * @date: 2021/4/29 10:47
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 删除指定用户
     *
     * @param id:
     * @author: lots
     * @return: int
     * @date: 2021/4/29 10:52
     */
    int delete(Long id);

    /**
     * 修改用户角色关系
     *
     * @param userId:  用户ID
     * @param roleIds: 角色IDS
     * @author: lots
     * @return: int
     * @date: 2021/4/29 10:58
     */
    @Transactional(rollbackFor = {Exception.class})
    int updateRole(Long userId, List<Long> roleIds);

}