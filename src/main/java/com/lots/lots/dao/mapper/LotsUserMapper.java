package com.lots.lots.dao.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.lots.lots.entity.vo.LotsUserVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * (LotsUser)表数据库访问层
 *
 * @author default
 * @since 2020-08-24 11:44:40
 */
@Component
public interface LotsUserMapper {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    LotsUserVo findById(Long id);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param lotsUser 实例对象
     * @return 对象列表
     */
    List<LotsUserVo> queryAll(LotsUserVo lotsUser);

    /**
     * 新增数据
     *
     * @param lotsUser 实例对象
     * @return 影响行数
     */
    int insert(LotsUserVo lotsUser);

    /**
     * 修改数据
     *
     * @param lotsUser 实例对象
     * @return 影响行数
     */
    int update(LotsUserVo lotsUser);

    /**
     * 通过主键删除数据
     *
     * @param id
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据username获取用户信息
     *
     * @param username:
     * @author: lots
     * @return: com.lots.lots.entity.vo.LotsUserVo
     * @date: 2021/4/29 10:51
     */
    LotsUserVo getAdminByUsername(String username);

    /**
     * 根据名称获取用户信息
     *
     * @param keyword:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsUserVo>
     * @date: 2021/4/29 10:50
     */
    List<LotsUserVo> findByName(@Param("keyword") String keyword);

    /**
     * 根据名称获取用户信息
     *
     * @param keyword:
     * @author: lots
     * @return: java.util.List<com.lots.lots.entity.vo.LotsUserVo>
     * @date: 2021/4/29 10:50
     */
    IPage<LotsUserVo> findByNamePage(Page page, @Param("keyword") String keyword);

    /**
     * 修改密码
     *
     * @param lotsUser:
     * @author: lots
     * @return: int
     * @date: 2021/4/29 10:50
     */
    int updateByPrimaryKey(LotsUserVo lotsUser);
}