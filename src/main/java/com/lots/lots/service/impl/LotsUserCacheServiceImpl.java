package com.lots.lots.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.lots.lots.dao.mapper.LotsUserRoleRelationMapper;
import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.entity.vo.LotsUserRoleRelationVo;
import com.lots.lots.entity.vo.LotsUserVo;
import com.lots.lots.service.LotsUserCacheService;
import com.lots.lots.service.LotsUserService;
import com.lots.lots.service.RedisService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UmsAdminCacheService实现类
 *
 * @author lots
 */
@Service
public class LotsUserCacheServiceImpl implements LotsUserCacheService {

    @Resource
    private RedisService redisService;
    @Resource
    private LotsUserRoleRelationMapper lotsUserRoleRelationMapper;

    @Value("${redis.database}")
    private String redisDataBase;
    @Value("${redis.expire.common}")
    private Long redisExpire;
    @Value("${redis.key.admin}")
    private String redisKeyAdmin;
    @Value("${redis.key.resourceList}")
    private String redisKeyResourceList;

    @Override
    public void delAdmin(String username) {
        List<LotsUserRoleRelationVo> relationList = lotsUserRoleRelationMapper.findRoleIdIn(roleIds);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = redisDataBase + ":" + redisKeyResourceList + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getUserId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceList(Long userId) {
        LotsUserRoleRelationVo vo = new LotsUserRoleRelationVo();
        vo.setRoleId(roleId);
        List<LotsUserRoleRelationVo> relationList = lotsUserRoleRelationMapper.queryAll(vo);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = redisDataBase + ":" + redisKeyResourceList + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getUserId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByRole(Long roleId) {
        LotsUserRoleRelationVo vo = new LotsUserRoleRelationVo();
        vo.setRoleId(roleId);
        List<LotsUserRoleRelationVo> relationList = lotsUserRoleRelationMapper.queryAll(vo);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = redisDataBase + ":" + redisKeyResourceList + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getUserId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        List<LotsUserRoleRelationVo> relationList = lotsUserRoleRelationMapper.findRoleIdIn(roleIds);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = redisDataBase + ":" + redisKeyResourceList + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getUserId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByResource(Long resourceId) {
        List<Long> userIdList = lotsUserRoleRelationMapper.getUserIdList(resourceId);
        if (CollUtil.isNotEmpty(userIdList)) {
            String keyPrefix = redisDataBase + ":" + redisKeyResourceList + ":";
            List<String> keys = userIdList.stream().map(userId -> keyPrefix + userId).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public LotsUserVo getAdmin(String username) {
        String key = redisDataBase + ":" + redisKeyAdmin + ":" + username;
        return (LotsUserVo) redisService.get(key);
    }

    @Override
    public void setAdmin(LotsUserVo admin) {
        String key = redisDataBase + ":" + redisKeyAdmin + ":" + admin.getUsername();
        redisService.set(key, admin, redisExpire);
    }

    @Override
    public List<LotsResourceVo> getResourceList(Long userId) {
        String key = redisDataBase + ":" + redisKeyResourceList + ":" + userId;
        return (List<LotsResourceVo>) redisService.get(key);
    }

    @Override
    public void setResourceList(Long userId, List<LotsResourceVo> resourceList) {
        String key = redisDataBase + ":" + redisKeyResourceList + ":" + userId;
        redisService.set(key, resourceList, redisExpire);
    }
}
