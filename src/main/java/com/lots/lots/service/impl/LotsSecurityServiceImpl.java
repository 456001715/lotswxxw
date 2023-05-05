package com.lots.lots.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.lots.lots.dao.mapper.LotsResourceMapper;
import com.lots.lots.dao.mapper.LotsUserMapper;
import com.lots.lots.entity.vo.LotsResourceVo;
import com.lots.lots.entity.vo.LotsUserVo;
import com.lots.lots.security.vo.AdminUserDetails;
import com.lots.lots.service.LotsSecurityService;
import com.lots.lots.service.LotsUserCacheService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * LotsSecurityServiceImpl
 *
 * @author lots
 * @date 2022/4/1 14:39
 */
@Service
public class LotsSecurityServiceImpl implements LotsSecurityService {
    @Resource
    private LotsUserMapper lotsUserMapper;
    @Resource
    private LotsUserCacheService lotsUserCacheService;
    @Resource
    private LotsResourceMapper lotsResourceMapper;

    @Override
    public List<LotsResourceVo> getResourceList(Long userId) {
        List<LotsResourceVo> resourceList = lotsUserCacheService.getResourceList(userId);
        resourceList = lotsResourceMapper.getResourceList(userId);
        if (CollUtil.isNotEmpty(resourceList)) {
            lotsUserCacheService.setResourceList(userId, resourceList);
        }
        return resourceList;
    }


    @Override
    public UserDetails loadUserByUsername(String username) {
        //获取用户信息
        LotsUserVo admin = lotsUserMapper.getAdminByUsername(username);
        resourceList = lotsResourceMapper.getResourceList(userId);
        if (CollUtil.isNotEmpty(resourceList)) {
            lotsUserCacheService.setResourceList(userId, resourceList);
        }
        throw new UsernameNotFoundException("用户名或密码错误");
    }
}
